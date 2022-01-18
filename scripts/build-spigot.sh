#!/usr/bin/env bash
mkdir -p $1/libraries/spigot
echo "Downloading Spigot files..."
versions=$1/scripts/data/versions.txt
while read line; do
  if [ -z "$line" ]; then
    continue
  fi
  echo "Downloading Spigot $line"
  curl "http://test.playuniverse.org/host/download/github/spigot-$line.jar"  --output "$1/libraries/spigot/spigot-$line.jar"
done < $versions
echo "Download complete!"
