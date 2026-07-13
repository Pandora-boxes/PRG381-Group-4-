PRG381 Group 4 - Derby jars go in THIS folder
=============================================

The project runs on Apache Derby 10.16.1.1 (EMBEDDED). It targets Java 17.
Do NOT use Derby 10.17 - that version needs Java 21 and will not work here.

Put these three files in this folder (PRG381Group4/lib/):

    derby.jar
    derbyshared.jar
    derbytools.jar

Where to get them
-----------------
1. Download db-derby-10.16.1.1-bin.zip from:
       https://db.apache.org/derby/derby_downloads.html
2. Unzip it. The three jars above are inside the download's own "lib" folder.
3. Copy them into this folder.

These jars are committed to the repo on purpose (the .gitignore has a
"!PRG381Group4/lib/*.jar" rule that keeps them), so once one person adds them
and pushes, everyone else just pulls.

Add them to the NetBeans classpath (each team member does this once)
--------------------------------------------------------------------
Right-click the project -> Properties -> Libraries -> Classpath ->
Add JAR/Folder -> select all three jars from this folder.

IMPORTANT: when NetBeans asks, choose the RELATIVE PATH option.
If you pick Absolute, the classpath points at your own C:\ drive and the
project breaks on every other laptop.
