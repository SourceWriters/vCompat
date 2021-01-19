#!/usr/bin/env bash
echo "::info::Downloading Spigot BuildTools..."
echo "::info::Path is set to $1"
mkdir -p $1/libraries/spigot
mkdir $1/spigotWork
curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar --output $1/spigotWork/BuildTools.jar
echo "::info::Successfully downloaded BuildTools"

echo "::info::Building Spigot files..."
versions=$1/data/versions.txt
while read line; do
  if [ -z "$line"]; then
    continue
  fi
  echo "::info::Building Spigot " + $line
done < $versions
echo "::info::Building complete!"
