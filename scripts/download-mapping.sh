#!/usr/bin/env bash
mkdir -p $1/libraries/mapping
mkdir -p $1/libraries/spigot
echo "Downloading Server Mapping files..."
versions=$1/scripts/data/mappings.txt
while read line; do
  if [ -z "$line" ]; then
    continue
  fi
  echo "Downloading Server Mappings (.txt) $line"
  curl "http://test.playuniverse.org/host/download/github/mapping/mapping-$line.txt"  --output "$1/libraries/mapping/mapping-$line.txt"
  echo "Downloading Server Mappings (.csrg) $line"
  curl "http://test.playuniverse.org/host/download/github/mapping/mapping-$line.csrg"  --output "$1/libraries/mapping/mapping-$line.csrg"
  echo "Downloading Server Mappings (.jar) $line"
  curl "http://test.playuniverse.org/host/download/github/mapping/spigot-$line-obf.jar"  --output "$1/libraries/spigot/spigot-$line-obf.jar"
done < $versions
echo "Download complete!"
