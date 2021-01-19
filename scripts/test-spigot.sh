#!/usr/bin/env bash
echo "::info::Downloading Spigot BuildTools..."
PATH=$1
echo "::info::Path is set to $path"
mkdir -p $PATH/libraries/spigot
mkdir $PATH/spigotWork
curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar --output $PATH/spigotWork/BuildTools.jar
echo "::info::Successfully downloaded BuildTools"

echo "::info::Building Spigot files..."
versions=$PATH/data/versions.txt
while read line; do
  if [ -z "$line"]; then
    continue
  fi
  echo "::info::Building Spigot " + $line
done < $versions
echo "::info::Building complete!"
