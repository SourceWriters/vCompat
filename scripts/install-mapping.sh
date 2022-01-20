#!/usr/bin/env bash
mkdir -p $1/libraries/mapping
mkdir -p $1/libraries/spigot
echo "Install Server Mapping files..."
versions=$1/scripts/data/mappings.txt
while read line; do
  if [ -z "$line" ]; then
    continue
  fi
  echo "Install Server Mappings (.jar / remapped) $line"
  mvn install:install-file -Dfile=$1/libraries/spigot/spigot-$line.jar -Dpackaging=jar -DgroupId=org.spigotmc -DartifactId=spigot -Dversion=$line-R0.1-SNAPSHOT -Dclassifier=remapped-mojang
  echo "Install Server Mappings (.jar / obfuscated) $line"
  mvn install:install-file -Dfile=$1/libraries/spigot/spigot-$line-obf.jar -Dpackaging=jar -DgroupId=org.spigotmc -DartifactId=spigot -Dversion=$line-R0.1-SNAPSHOT -Dclassifier=remapped-obf
  echo "Install Server Mappings (.txt) $line"
  mvn install:install-file -Dfile=$1/libraries/mapping/mapping-$line.txt -Dpackaging=txt -DgroupId=org.spigotmc -DartifactId=minecraft-server -Dversion=$line-R0.1-SNAPSHOT -Dclassifier=maps-mojang
  echo "Install Server Mappings (.csrg) $line"
  mvn install:install-file -Dfile=$1/libraries/mapping/mapping-$line.csrg -Dpackaging=csrg -DgroupId=org.spigotmc -DartifactId=minecraft-server -Dversion=$line-R0.1-SNAPSHOT -Dclassifier=maps-spigot
done < $versions
echo "Installation complete!"
