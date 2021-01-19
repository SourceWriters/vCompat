#!/usr/bin/env bash
echo "Downloading Spigot BuildTools..."
curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar --output BuildTools.jar
echo "Successfully downloaded BuildTools"

echo "Building Spigot files..."
versions=${{ github.workspace }}/scripts/data/versions.txt
while read line; do
  if [ -z "$line"]; then
    continue
  fi
  echo "Building Spigot " + $line
  java -jar BuildTools.jar --rev $line
done < $versions
echo "Building complete!"
