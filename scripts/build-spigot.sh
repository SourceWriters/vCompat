#!/usr/bin/env bash
echo "Downloading Spigot BuildTools..."
mkdir -p ${{ github.workspace }}/libraries/spigot
mkdir ${{ github.workspace }}/spigotWork
curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar --output ${{ github.workspace }}/spigotWork/BuildTools.jar
echo "Successfully downloaded BuildTools"

echo "Building Spigot files..."
versions=${{ github.workspace }}/data/versions.txt
while read line; do
  if [ -z "$line"]; then
    continue
  fi
  echo "Building Spigot " + $line
  java -jar ${{ github.workspace }}/spigotWork/BuildTools.jar --rev $line --outputDir ../libraries/spigot
done < $versions
echo "Building complete!"
