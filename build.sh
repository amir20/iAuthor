#!/bin/bash
rm -rf build
mkdir -p build/lib
mkdir -p build/tools
mvn -Dmaven.test.skip=true clean package
cp -v `find application/target/lib/ -type f -not -name "*tool-1.0.jar"` build/lib/
cp -v `find application/target/lib/ -type f -name "*tool-1.0.jar"` build/tools/
cp -v application/target/application-1.0.jar build/lib/
cp -rv application/src/main/resources/lucene build/
cp -v application/src/main/resources/images/icon.png build/
echo "java -Xdock:name="iAuthor" -Xdock:icon=icon.png -cp .:lib/*:tools/* edu.gwu.raminfar.iauthor.Main" > build/run.sh
chmod 755 build/run.sh

