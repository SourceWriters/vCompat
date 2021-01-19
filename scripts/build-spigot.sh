#!/usr/bin/env bash
echo "Downloading Spigot BuildTools..."
var path = $1
mkdir -p $path/libraries/spigot
mkdir $path/spigotWork
curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar --output $path/spigotWork/BuildTools.jar
echo "Successfully downloaded BuildTools"

echo "Building Spigot files..."
versions=$path/data/versions.txt
while read line; do
  if [ -z "$line"]; then
    continue
  fi
  echo "Building Spigot " + $line
  java -jar $path/spigotWork/BuildTools.jar --rev $line --outputDir ../libraries/spigot
done < $versions
echo "Building complete!"
