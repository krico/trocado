var gulp = require('gulp');
var plug = require('gulp-load-plugins')();

var paths = {
    target: {
        webapp: 'target/trocado-1.0-SNAPSHOT',
        build: 'target/trocado-1.0-SNAPSHOT/build',
        partials: 'target/trocado-1.0-SNAPSHOT/partials'
    },
    client: {
        js: ['src/main/client/**/*.module.js', 'src/main/client/**/*.js'],
        partials: ['src/main/client/**/*.html'],
        markup: ['src/main/webapp/**/*.html'],
        style: ['src/main/styles/**/*.less']
    }
};

gulp.task('client-js', clientJs);
gulp.task('client-markup', clientMarkup);
gulp.task('client-partials', clientPartials);
gulp.task('client-style', clientStyle);
gulp.task('watch', watchIt);

gulp.task('build', ['client-js', 'client-markup', 'client-style', 'client-partials']);
gulp.task('default', ['build', 'watch']);

function watchIt() {
    gulp.watch(paths.client.js, ['client-js']);
    gulp.watch(paths.client.markup, ['client-markup']);
    gulp.watch(paths.client.style, ['client-style']);
    gulp.watch(paths.client.partials, ['client-partials']);
}

function clientStyle() {
    return gulp.src(paths.client.style)
        .pipe(plug.plumber())
        .pipe(plug.less())
        .pipe(plug.concat('trocado.css'))
        .pipe(gulp.dest(paths.target.build));
}

function clientJs() {
    return gulp.src(paths.client.js)
        .pipe(plug.plumber())
        .pipe(plug.ngAnnotate())
        .pipe(plug.sourcemaps.init())
        .pipe(plug.concat('trocado.js'))
        .pipe(gulp.dest(paths.target.build))
        .pipe(plug.uglify())
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.sourcemaps.write('./'))
        .pipe(gulp.dest(paths.target.build));
}

function clientMarkup() {
    return gulp.src(paths.client.markup)
        .pipe(plug.plumber())
        .pipe(gulp.dest(paths.target.webapp));
}
function clientPartials() {
    return gulp.src(paths.client.partials)
        .pipe(plug.plumber())
        .pipe(plug.htmlmin({
            collapseWhitespace: true,
            removeAttributeQuotes: true,
            removeComments: true
        }))
        .pipe(plug.angularTemplatecache({
            filename: 'trocado.tpl.js',
            module: 'trocado.templates'
        }))
        .pipe(plug.wrapper({
            header: '(function(angular){',
            footer: '})(angular);'
        }))
        .pipe(plug.sourcemaps.init())
        .pipe(gulp.dest(paths.target.build))
        .pipe(plug.ngAnnotate())
        .pipe(plug.uglify())
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.sourcemaps.write('./'))
        .pipe(gulp.dest(paths.target.build));
}
