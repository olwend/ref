module.exports = function (grunt) {
// load all grunt tasks matching the `grunt-*` pattern
require('load-grunt-tasks')(grunt);

grunt.initConfig({
	//compile SCSS files into CSS.
	sass: {
		dynamicapps: {
			files: {
				'designs/nhmwww/css/nhm-legacy-global.css': 'scss/dynamicapps/nhm-legacy-global/styles.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		bombus: {
			files: {
				'designs/nhmwww/css/nhm-legacy-bombus.css': 'scss/dynamicapps/bombus/nhm-legacy-bombus.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		butmoth: {
			files: {
				'designs/nhmwww/css/nhm-legacy-butmoth.css': 'scss/dynamicapps/butmoth/nhm-legacy-butmoth.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		chalcidoidea: {
			files: {
				'designs/nhmwww/css/nhm-legacy-chalcidoidea.css': 'scss/dynamicapps/chalcidoidea/nhm-legacy-chalcidoidea.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		cockayne: {
			files: {
				'designs/nhmwww/css/nhm-legacy-cockayne.css': 'scss/dynamicapps/cockayne/nhm-legacy-cockayne.scss',
				},
			options: {
				outputStyle: 'expanded'
			}
		},

		commercialtraining: {
			files: {
				'designs/nhmwww/css/commercial-training-payment-form.css': 'scss/dynamicapps/commercial-training-payment-form/styles.scss',
				'designs/nhmwww/css/commercial-training-payment-credit-card-page.css': 'scss/dynamicapps/commercial-training-payment-form/credit-card-page.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		contactus: {
			files: {
				'designs/nhmwww/css/nhm-legacy-contact-us-form.css': 'scss/dynamicapps/contactus/styles.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		dinodirectory: {
			files: {
				'designs/nhmwww/css/nhm-legacy-dino-directory.css': 'scss/dynamicapps/dinodirectory/nhm-legacy-dinodirectory.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		echinoids: {
			files: {
				'designs/nhmwww/css/nhm-legacy-echinoids.css': 'scss/dynamicapps/echinoids/nhm-legacy-echinoids.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		endeavour: {
			files: {
				'designs/nhmwww/css/nhm-legacy-endeavour.css': 'scss/dynamicapps/endeavour/nhm-legacy-endeavour.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		lephosts: {
			files: {
				'designs/nhmwww/css/nhm-legacy-lep-hosts.css': 'scss/dynamicapps/lepidopteran-hostplants/nhm-legacy-lep-hosts.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		lepindex: {
			files: {
				'designs/nhmwww/css/nhm-legacy-lepindex.css': 'scss/dynamicapps/lepidoptera/nhm-legacy-lepindex.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		lichenid: {
			files: {
				'designs/nhmwww/css/nhm-legacy-lichenid.css': 'scss/dynamicapps/lichenid/nhm-legacy-lichenid.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		linnaeantypification: {
			files: {
				'designs/nhmwww/css/nhm-legacy-linnaeantypification.css': 'scss/dynamicapps/linnaeantypification/nhm-legacy-linnaeantypification.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		macgillivray: {
			files: {
				'designs/nhmwww/css/nhm-legacy-macgillivray.css': 'scss/dynamicapps/macgillivray/nhm-legacy-macgillivray.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		membership: {
			files: {
				'designs/nhmwww/css/membership.css': 'scss/dynamicapps/membership/styles.scss',
				'designs/nhmwww/css/membership-credit-card-page.css': 'scss/dynamicapps/membership/credit-card-page.scss',
			},
			options: {
				outputStyle: 'expanded'
			},
		},

		metcat: {
			files: {
				'designs/nhmwww/css/nhm-legacy-metcat.css': 'scss/dynamicapps/metcat/nhm-legacy-metcat.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		naturegroups: {
			files: {
				'designs/nhmwww/css/nhm-legacy-ngny.css': 'scss/dynamicapps/naturegroups/styles.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		natureplus: {
			files: {
				'designs/nhmwww/css/nhm-legacy-natureplus.css': 'scss/dynamicapps/natureplus/styles.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		patronmail: {
			files: {
				'designs/nhmwww/css/nhm-legacy-patronmail.css': 'scss/dynamicapps/patronMail/nhm-legacy-patronmail.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		picturelibrary: {
			files: {
				'designs/nhmwww/css/nhm-legacy-piclib.css': 'scss/dynamicapps/picturelibrary/nhm-legacy-piclib.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		profilemanager: {
			files: {
				'designs/nhmwww/css/nhm-legacy-profile-manager.css': 'scss/dynamicapps/profileManager/nhm-legacy-profile-manager.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		protistvideo: {
			files: {
				'designs/nhmwww/css/nhm-legacy-protistvideo.css': 'scss/dynamicapps/protistvideo/nhm-legacy-protistvideo.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		speciesdictionary: {
			files: {
				'designs/nhmwww/css/nhm-legacy-species-dictionary.css': 'scss/dynamicapps/speciesdictionary/nhm-legacy-species-dictionary.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		spruce: {
			files: {
				'designs/nhmwww/css/nhm-legacy-spruce.css': 'scss/dynamicapps/spruce/nhm-legacy-spruce.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},

		tropicalsnails: {
			files: {
				'designs/nhmwww/css/nhm-legacy-tropical-snails.css': 'scss/dynamicapps/tropical-land-snails/nhm-legacy-tropical-snails.scss',
			},
			options: {
				outputStyle: 'expanded'
			}
		},
	},

	//watch for changes to SCSS files.
	watch: {
		dynamicapps: {
			files: ['scss/dynamicapps/nhm-legacy-global/styles.scss'],
			tasks: ['sass:dynamicapps','notify:sass'],
		},

		bombus: {
			files: ['scss/dynamicapps/bombus/nhm-legacy-bombus.scss'],
			tasks: ['sass:bombus','notify:sass'],
		},

		butmoth: {
			files: ['scss/dynamicapps/butmoth/nhm-legacy-butmoth.scss'],
			tasks: ['sass:butmoth','notify:sass'],
		},

		chalcidoidea: {
			files: ['scss/dynamicapps/chalcidoidea/nhm-legacy-chalcidoidea.scss'],
			tasks: ['sass:chalcidoidea','notify:sass'],
		},

		cockayne: {
			files: ['scss/dynamicapps/cockayne/nhm-legacy-cockayne.scss'],
			tasks: ['sass:cockayne','notify:sass'],
		},

		commercialtraining: {
			files: ['scss/dynamicapps/commercial-training-payment-form/*.scss', 'scss/dynamicapps/commercial-training-payment-form/credit-card-page.scss'],
			tasks: ['sass:commercialtraining','notify:sass'],
		},

		contactus: {
			files: ['scss/dynamicapps/contactus/styles.scss'],
			tasks: ['sass:contactus','notify:sass'],
		},

		dinodirectory: {
			files: ['scss/dynamicapps/dinodirectory/nhm-legacy-dinodirectory.scss'],
			tasks: ['sass:dinodirectory','notify:sass'],
		},

		echinoids: {
			files: ['scss/dynamicapps/echinoids/nhm-legacy-echinoids.scss'],
			tasks: ['sass:echinoids','notify:sass'],
		},

		endeavour: {
			files: ['scss/dynamicapps/endeavour/nhm-legacy-endeavour.scss'],
			tasks: ['sass:endeavour','notify:sass'],
		},

		lephosts: {
			files: ['scss/dynamicapps/lepidopteran-hostplants/nhm-legacy-lep-hosts.scss'],
			tasks: ['sass:lephosts','notify:sass'],
		},

		lepindex: {
			files: ['scss/dynamicapps/lepidoptera/nhm-legacy-lepindex.scss'],
			tasks: ['sass:lepindex','notify:sass'],
		},

		lichenid: {
			files: ['scss/dynamicapps/lichenid/nhm-legacy-lichenid.scss'],
			tasks: ['sass:lichenid','notify:sass'],
		},

		linnaeantypification: {
			files: ['scss/dynamicapps/linnaeantypification/nhm-legacy-linnaeantypification.scss'],
			tasks: ['sass:linnaeantypification','notify:sass'],
		},

		macgillivray: {
			files: ['scss/dynamicapps/macgillivray/nhm-legacy-macgillivray.scss'],
			tasks: ['sass:macgillivray','notify:sass'],
		},

		membership: {
			files: ['scss/dynamicapps/membership/*.scss','scss/dynamicapps/membership/credit-card-page.scss'],
			tasks: ['sass:membership','notify:sass'],
		},

		metcat: {
			files: ['scss/dynamicapps/metcat/nhm-legacy-metcat.scss'],
			tasks: ['sass:metcat','notify:sass'],
		},

		naturegroups: {
			files: ['scss/dynamicapps/naturegroups/styles.scss'],
			tasks: ['sass:naturegroups','notify:sass'],
		},

		natureplus: {
			files: ['scss/dynamicapps/natureplus/styles.scss'],
			tasks: ['sass:natureplus','notify:sass'],
		},

		patronmail: {
			files: ['scss/dynamicapps/patronMail/nhm-legacy-patronmail.scss'],
			tasks: ['sass:patronmail','notify:sass'],
		},

		picturelibrary: {
			files: ['scss/dynamicapps/picturelibrary/nhm-legacy-piclib.scss'],
			tasks: ['sass:picturelibrary','notify:sass'],
		},

		profilemanager: {
			files: ['scss/dynamicapps/profileManager/nhm-legacy-profile-manager.scss'],
			tasks: ['sass:profilemanager','notify:sass'],
		},

		protistvideo: {
			files: ['scss/dynamicapps/protistvideo/nhm-legacy-protistvideo.scss'],
			tasks: ['sass:protistvideo','notify:sass'],
		},

		speciesdictionary: {
			files: ['scss/dynamicapps/speciesdictionary/nhm-legacy-species-dictionary.scss'],
			tasks: ['sass:speciesdictionary','notify:sass'],
		},

		spruce: {
			files: ['scss/dynamicapps/spruce/nhm-legacy-spruce.scss'],
			tasks: ['sass:spruce','notify:sass'],
		},

		tropicalsnails: {
			files: ['scss/dynamicapps/tropical-land-snails/nhm-legacy-tropical-snails.scss'],
			tasks: ['sass:tropicalsnails','notify:sass'],
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

	grunt.registerTask('develop-dynamicapps', ['sass:dynamicapps', 'watch:dynamicapps']);
	grunt.registerTask('develop-bombus', ['sass:bombus', 'watch:bombus']);
	grunt.registerTask('develop-butmoth', ['sass:butmoth', 'watch:butmoth']);
	grunt.registerTask('develop-chalcidoidea', ['sass:chalcidoidea', 'watch:chalcidoidea']);
	grunt.registerTask('develop-cockayne', ['sass:cockayne', 'watch:cockayne']);
	grunt.registerTask('develop-commercialtraining', ['sass:commercialtraining', 'watch:commercialtraining']);
	grunt.registerTask('develop-contactus', ['sass:contactus', 'watch:contactus']);
	grunt.registerTask('develop-dinodirectory', ['sass:dinodirectory', 'watch:dinodirectory']);
	grunt.registerTask('develop-echinoids', ['sass:echinoids', 'watch:echinoids']);
	grunt.registerTask('develop-endeavour', ['sass:endeavour', 'watch:endeavour']);
	grunt.registerTask('develop-lephosts', ['sass:lephosts', 'watch:lephosts']);
	grunt.registerTask('develop-lepindex', ['sass:lepindex', 'watch:lepindex']);
	grunt.registerTask('develop-lichenid', ['sass:lichenid', 'watch:lichenid']);
	grunt.registerTask('develop-linnaeantypification', ['sass:linnaeantypification', 'watch:linnaeantypification']);
	grunt.registerTask('develop-naturegroups', ['sass:naturegroups', 'watch:naturegroups']);
	grunt.registerTask('develop-natureplus', ['sass:natureplus', 'watch:natureplus']);
	grunt.registerTask('develop-macgillivray', ['sass:macgillivray', 'watch:macgillivray']);
	grunt.registerTask('develop-membership', ['sass:membership', 'watch:membership']);
	grunt.registerTask('develop-metcat', ['sass:metcat', 'watch:metcat']);
	grunt.registerTask('develop-patronmail', ['sass:patronmail', 'watch:patronmail']);
	grunt.registerTask('develop-picturelibrary', ['sass:picturelibrary', 'watch:picturelibrary']);
	grunt.registerTask('develop-profilemanager', ['sass:profilemanager', 'watch:profilemanager']);
	grunt.registerTask('develop-protistvideo', ['sass:protistvideo', 'watch:protistvideo']);
	grunt.registerTask('develop-speciesdictionary', ['sass:speciesdictionary', 'watch:speciesdictionary']);
	grunt.registerTask('develop-spruce', ['sass:spruce', 'watch:spruce']);
	grunt.registerTask('develop-tropicalsnails', ['sass:tropicalsnails', 'watch:tropicalsnails']);
};
