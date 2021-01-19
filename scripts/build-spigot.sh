#!/usr/bin/env bash
echo "Downloading Spigot BuildTools..."
mkdir -p $1/libraries/spigot
mkdir $1/spigotWork
curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar --output $1/spigotWork/BuildTools.jar
echo "Successfully downloaded BuildTools"

echo "Building Spigot files..."
versions=$1/scripts/data/versions.txt
while read line; do
  if [ -z "$line"]; then
    continue
  fi
  echo "Building Spigot " + $line
  java -jar $1/spigotWork/BuildTools.jar --rev $line --outputDir ../libraries/spigot
done < $versions
echo "Building complete!"
