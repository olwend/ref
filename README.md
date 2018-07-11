# nhm-aem-www (NHM AEM Git repo)

This is the Git repo for the NHM website, built on Adobe Experience Manager (AEM).

## Instructions

The below instructions assume you already have the AEM JAR, Service Packs and other relevant packages installed.

### To set up a local AEM development environment for the first time, follow these steps:

1. Clone the repo:
`` git clone git@git-repo-2.nhm.ac.uk:digital-development/nhm-aem-www.git ``

2.	Create a new BAT file to use for launching AEM on port 4502. Call the BAT file something like "AEM launch.bat" and put it somewhere useful e.g. on your Desktop. The BAT file should look like this:
`` start "" /D "path/to/your/aem/instance" "path/to/your/git-bash-instance" -c "java -Xms1024m -Xmx4096m -jar <aem-jar-file-name>" ``

3.	Create a new BAT file to use for launching AEM Browsersync. Call the BAT file something like "AEM browsersync.bat" and put it somewhere useful e.g. on your Desktop. The BAT file should look like this:
`` start "" /D "path/to/your/nhm-aem-codebase" "path/to/your/git-bash-instance" -c "aem-front" ``

4.	Install Node from https://nodejs.org/en/download (LTS is fine)

5.	Check Node and NPM have been installed correctly by running `` node –v `` and `` npm –v ``

6.	Navigate to `` /ui.apps/src/main/content/jcr_root/etc `` and run `` npm install ``

7.	If you don’t have Grunt installed, install the Grunt CLI with `` npm install –g grunt-cli ``

8.	Check Grunt and Grunt CLI have been installed correctly by running `` grunt --version ``
9.  Run `` npm install -g aem-front `` to install AEM Browsersync

### To work on the nhm-aem-www codebase:
1. Launch your local AEM instance using the BAT file.

2. Build your selected branch.
3. Launch Browsersync using the BAT file
4. Run `` grunt develop ``
5. When you make any changes to the SCSS files in `` /etc/scss `` or the JS files in `` /etc/clientlibs `` or a template file, AEM Browsersync will automatically run a build and refresh the browser (though you may need to manually hard-refresh as well).

Note you will need to manually build if you make any changes to the Core Java files.
