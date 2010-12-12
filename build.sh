rm -rf bin
mkdir -p bin/lib
mkdir -p bin/tools
mvn -Dmaven.test.skip=true clean package
cp -v `find application/target/lib/ -type f -not -name "*tool-1.0.jar"` bin/lib/
cp -v `find application/target/lib/ -type f -name "*tool-1.0.jar"` bin/tools/
cp -v application/target/application-1.0.jar bin/lib/
cp -rv application/src/main/resources/lucene bin/

