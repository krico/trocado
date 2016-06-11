/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.providers').provider('Repeater', RepeaterProvider);

    function RepeaterProvider() {
        this._pageSize = 50;
        this._maxPages = 5;
        this._debug = false;
    }

    RepeaterProvider.prototype.pageSize = function (pageSize) {
        if (angular.isDefined(pageSize)) {
            this._pageSize = pageSize;
        }
        return this;
    };

    RepeaterProvider.prototype.debug = function (debug) {
        if (angular.isDefined(debug)) {
            this._debug = debug;
        }
        return this;
    };

    RepeaterProvider.prototype.maxPages = function (maxPages) {
        if (angular.isDefined(maxPages)) {
            this._maxPages = maxPages;
        }
        return this;
    };


    RepeaterProvider.prototype.$get = function ($log, Dialog) {
        var provider = this;

        function Page(id) {
            this.id = id;
            this.next = null;
            this.prev = null;
            this.data = [];
        }

        Page.prototype.toString = function () {
            return 'Page' +
                '[id=' + this.id + ']'
                ;
        };

        Page.prototype.item = function (i) {
            return this.data[i];
        };

        function PageCache(loadCallback) {
            if (!angular.isFunction(loadCallback)) {
                throw ("PageCache(loadCallback): loadCallback must be a function");
            }
            if (provider._maxPages < 2) {
                throw ("maxPages must be >2");
            }
            this.size = 0;
            this.pages = {};
            this.head = null;
            this.tail = null;
            this.loaderCallback = loadCallback;
        }

        PageCache.prototype.getSize = function () {
            return this.size;
        };

        PageCache.prototype.insert = function (page) {
            if (angular.isDefined(this.pages[page.id])) {
                throw ("Duplicate page: " + page);
            }
            this.pages[page.id] = page;
            if (this.head === null) {
                //First page
                page.next = null;
                page.prev = null;
                this.head = page;
                this.tail = page;
            } else {
                page.next = this.head;
                this.head.prev = page;
                this.head = page;
            }

            if (this.size == provider._maxPages) {
                //Don't grow, remove tail
                var removed = this.tail;
                if (provider._debug) $log.debug('Removing ' + removed);
                delete this.pages[removed.id];
                this.tail = removed.prev;
                this.tail.next = null;

                removed.next = null;
                removed.prev = null;
            } else {
                this.size++;
            }
        };

        PageCache.prototype.touch = function (page) {
            if (page === this.head) return; //Alread at head

            if (page === this.tail) {
                this.tail = page.prev;
            }

            //Remove page from linked list
            page.prev.next = page.next;
            if (page.next !== null) page.next.prev = page.prev;

            page.next = this.head;
            this.head.prev = page;
            this.head = page;
            page.prev = null;
        };

        PageCache.prototype.getPage = function (pageNumber) {
            var page = this.pages[pageNumber];
            if (!page) {
                page = new Page(pageNumber);
                this.insert(page);
                this.loaderCallback(page);
            } else {
                this.touch(page);
            }
            return page;
        };

        function Repeater(endpoint) {
            if (!angular.isFunction(endpoint.query)) {
                throw ("endpoint must have method '.query'");
            }
            this.endpoint = endpoint;
            var that = this;
            this.cache = new PageCache(function (page) {
                return that.loadPage(page);
            });
            this.maxLength = 0;
            $log.debug('new ' + this);
            this.getItemAtIndex(0); //force load of first page
        }

        Repeater.prototype.indexToPage = function (index) {
            return parseInt(index / provider._pageSize, 10);
        };

        Repeater.prototype.toString = function () {
            return 'Repeater' +
                '[debug=' + provider._debug + ']' +
                '[cacheSize=' + this.cache.getSize() + ']' +
                '[pageSize=' + provider._pageSize + ']' +
                '[maxLength=' + this.maxLength + ']' +
                '[maxPages=' + provider._maxPages + ']'
                ;
        };

        Repeater.prototype.loadPage = function (page) {
            var offset = provider._pageSize * page.id;
            var limit = provider._pageSize;
            if (provider._debug) $log.debug(this + '.loadPage(' + page + ')->[' + offset + ' - ' + (offset + limit) + ']');

            var that = this;
            return this.endpoint.query(limit, offset).then(ok, fail);

            function ok(r) {
                var items = r.data.items;
                var end = page.id * provider._pageSize + items.length;
                if (end > that.maxLength) {
                    that.maxLength = end;
                    if (items.length === provider._pageSize) {
                        that.maxLength++; //we filled a page, must have another one
                    }
                }
                if (provider._debug) $log.debug(that + '.loadPage(' + page + ')->[' + offset + ' - ' + (offset + limit) + '][items=' + items.length + ']');

                for (var i = 0; i < items.length; ++i) {
                    page.data[i] = items[i];
                }

            }

            function fail(r) {
                if (provider._debug) $log.debug('FAILED: ' + that + '.loadPage(' + page + ')->[' + offset + ' - ' + (offset + limit) + '][r=' + angular.toJson(r) + ']');
                Dialog.showAlert('Refresh failed', 'Something went wrong...');
            }
        };


        /**
         * Implementation for mdVirtualRepeat
         * @param index to load
         * @returns {*} the item
         */
        Repeater.prototype.getItemAtIndex = function (index) {
            var pageNumber = this.indexToPage(index);
            var pageIndex = index % provider._pageSize;
            return this.cache.getPage(pageNumber).item(pageIndex);
        };

        /**
         * Implementation for mdVirtualRepeat
         * @returns {number} the number of items
         */
        Repeater.prototype.getLength = function () {
            return this.maxLength;
        };

        /**
         * Static method
         * @param endpoint to create a repeater for
         */
        Repeater.forEndpoint = function (endpoint) {
            return new Repeater(endpoint);
        };

        return Repeater;
    };

}(angular));
