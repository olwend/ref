module.exports = function (grunt) {
// load all grunt tasks matching the `grunt-*` pattern
require('load-grunt-tasks')(grunt);

grunt.initConfig({

			// Copy styles.css and main.js
			copy: {
				css: {
					files: [
					{expand: true,
					cwd: 'clientlibs/nhmwww/main/css/',
					src: 'styles.css',
					dest: 'designs/nhmwww/css/'},
					]
				},
				js: {
					files: [
					{expand: true,
					cwd: 'clientlibs/nhmwww/main/js/',
					src: 'main.js',
					dest: 'designs/nhmwww/js/'},
					]
				}
			},

			// concat JS files
			concat: {
				options: {
					separator: ';',
				},
				dist: {
					src: [
						'etc/designs/nhmwww/js/vendor/jquery.hoverIntent.min.js',
						'etc/designs/nhmwww/js/vendor/fastclick.js',
						'etc/designs/nhmwww/js/vendor/jquery.cookie.js',
						'etc/designs/nhmwww/js/vendor/jquery.lightSlider.min.js',
						'etc/designs/nhmwww/js/vendor/modernizr.production.js',
						'etc/designs/nhmwww/js/vendor/placeholder.js',
						'etc/designs/nhmwww/js/vendor/rem.min.js',
						'etc/designs/nhmwww/js/vendor/respond.min.js',
						'etc/designs/nhmwww/js/vendor/snap.svg.js',
						'etc/designs/nhmwww/js/vendor/picturefill.min.js'
					],
					dest: 'etc/clientlibs/nhmwww/js/plugins.js',
				},
			},

			//compile SCSS files into CSS.
			sass: {
				foundation: {
					files: {
						'etc/designs/nhmwww/css/nhm-foundation.css': 'scss/nhm-foundation.scss',
					},
					options: {
						outputStyle: 'expanded'
					},
				},

				normalize: {
					files: {
						'etc/designs/nhmwww/css/normalize.css': 'scss/normalize.scss',
					},
					options: {
						outputStyle: 'expanded'
					},
				},

				web: {
					files: {
						'clientlibs/nhmwww/main/css/styles.css': 'scss/styles.scss',
					},
					options: {
						outputStyle: 'expanded'
					},
				},
			},

			//watch for changes to SCSS files.
			watch: {
				foundation: {
					files: ['scss/nhm-foundation.scss'],
					tasks: ['sass:foundation','notify:sass'],
				},

				normalize: {
					files: ['scss/normalize.scss'],
					tasks: ['sass:normalize','notify:sass'],
				},

				copy_css: {
					files: ['clientlibs/nhmwww/main/css/styles.css'],
					tasks: ['copy:css','notify:css'],
				},

				copy_js: {
					files: ['clientlibs/nhmwww/main/js/main.js'],
					tasks: ['copy:js','notify:js'],
				},

				web: {
					files: ['scss/styles.scss', 'scss/foundation.scss', 'scss/**/*.scss', '!scss/styles-desktop.scss', '!scss/normalize.scss', '!scss/dynamicapps/**/*.scss', '!scss/nhm-foundation.scss'],
					tasks: ['sass:web'],
				},
			},

			notify: {
				sass: {
					options: {
						title: "SCSS Compilation",
						message: "CSS compiled successfully",
						success: true
					}
				},
				css: {
					options: {
						title: "CSS",
						message: "CSS copied successfully",
						success: true
					}
				},
				js: {
					options: {
						title: "JS",
						message: "JS copied successfully",
						success: true
					}
				},
			}
		});

	grunt.registerTask('develop', ['sass:web', 'watch']);
	grunt.registerTask('buildjs', ['concat']);
	grunt.registerTask('develop-foundation', ['sass:foundation', 'watch:foundation']);
	grunt.registerTask('develop-normalize', ['sass:normalize', 'watch:normalize']);
};
