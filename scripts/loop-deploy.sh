#!/usr/bin/env bash
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
cd "${DIR}" || exit 1
. env.sh || exit 1

# initial deploy
. scripts/deploy.sh

# loop
while inotifywait -qq -r -e modify "$PLUGIN_SOURCE_DIR"; do
    gradle -q --daemon compileGroovy jar
    if [ "$?" != "0" ]; then 
        notify-send -t 3000 "Failed compilation.\n\n$RESULT"
        echo "$RESULT" 2>&1
    else
        . scripts/deploy.sh
        notify-send -t 3000 "Success, deployed."
    fi
done
