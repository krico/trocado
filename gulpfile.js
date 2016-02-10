var gulp = require('gulp');
var plug = require('gulp-load-plugins')();

gulp.task('client-js', clientJs);
gulp.task('client-markup', clientMarkup);
gulp.task('default', ['client-js', 'client-markup']);

function clientJs() {
    var dest = 'target/trocado-1.0-SNAPSHOT/build';
    return gulp.src(["src/main/client/**/*.module.js",
        "src/main/client/**/*.js"])
        .pipe(plug.plumber())
        .pipe(plug.ngAnnotate())
        .pipe(plug.sourcemaps.init())
        .pipe(plug.concat('trocado.js'))
        .pipe(gulp.dest(dest))
        .pipe(plug.uglify())
        .pipe(plug.rename({extname: '.min.js'}))
        .pipe(plug.sourcemaps.write('./'))
        .pipe(gulp.dest(dest));
}

function clientMarkup() {
    var dest = 'target/trocado-1.0-SNAPSHOT';
    return gulp.src(["src/main/webapp/**/*.html"])
        .pipe(plug.plumber())
        .pipe(gulp.dest(dest));
}
