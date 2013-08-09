#!/usr/bin/env bash

# Executes from top-level SDP dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${DIR}" || exit 1

# To add an additional item:
# 1. Add a function like one of the below
# 2. Add an entry to the CHOICES array at the bottom
# 3. Make sure the array position aligns with the function name
#    (index 0: function _1, index 1: function _2, etc.)

GO=$'\e[38;5;87m' 
WHITE=$'\e[38;5;255m'
GREY=$'\e[38;5;243m'
NONE=$'\e[0m'
RED=$'\e[38;5;196m'
GROUP1=$'\e[38;5;245m'
GROUP2=$'\e[38;5;239m'
CHOICE=$'\e[38;5;159m'
PROMPT="
${GO}go${WHITE}:${NONE} "

function script {
    echo -e "(${RED}${1}${NONE})"
    ./$@
    return $?
}

function _1 {
    echo "Cleaning..."
    echo
    script "clean.sh" || return $?
}

function _2 {
    echo "Building..."
    echo
    script "build.sh" || return $?
}

function _3 {
    echo "Testing..."
    echo
    script "test.sh" || return $?
}

function _4 {
    echo "Deploying..."
    echo
    script "deploy.sh" || return $?
}

function _5 {
    echo "Undeploying..."
    echo
    script "undeploy.sh" || return $?
}

function _6 {
    echo "Loop, compile!..."
    echo
    script "loop-compile.sh" || return $?
}

function _7 {
    echo "Loop, test!..."
    echo
    script "loop-test.sh" || return $?
}

function _8 {
    echo "Loop, compile/deploy!..."
    echo
    script "loop-deploy.sh" || return $?
}

function _9 {
    echo "Loop, compile/test/deploy!..."
    echo
    script "loop-test-deploy.sh" || return $?
}

function _10 {
    echo "Start..."
    echo
    script "start.sh" || return $?
}

function _11 {
    echo "Debug..."
    echo
    script "debug.sh" || return $?
}

function _12 {
    echo "Stop..."
    echo
    script "stop.sh" || return $?
}

# Each menu option should stand alone on its own line.
# Menu options correspond to functions above.
CHOICES=("${GROUP1}clean${NONE}" \
         "${GROUP1}build${NONE}" \
         "${GROUP1}test${NONE}" \
         "${GROUP1}deploy${NONE}" \
         "${GROUP1}undeploy${NONE}" \
         "${GROUP2}loop (compile)${NONE}" \
         "${GROUP2}loop (compile/test)${NONE}" \
         "${GROUP2}loop (compile/deploy)${NONE}" \
         "${GROUP2}loop (compile/test/deploy)${NONE}" \
         "${GROUP2}start${NONE}" \
         "${GROUP2}debug${NONE}" \
         "${GROUP2}stop${NONE}")

if [ $# -gt 0 ]; then
    echo
    echo "${GREY}Processing arguments before entering the shell.${NONE}"
    echo "${GREY}(${NONE}$@${GREY})${NONE}"
    echo
    for x in $@; do _$x || break; done
fi
echo
echo "${GO}Entering go shell, [CTRL-C] to exit.${NONE}"

function menu() {
    declare -i i=0
    declare -i cols=3
    declare -i nl=$(expr $cols - 1)
    tabs -28
    while [ $i -lt ${#CHOICES[@]} ]; do
        echo -en "${CHOICE}$(expr $i + 1))${NONE} ${CHOICES[$i]}\t"
        if [ $nl -eq $i ]; then
            echo
            nl=$nl+$cols
        fi
        i=$i+1
    done
    echo
    tabs -8
}

menu
while true; do
    echo -en "$PROMPT"
    read REPLY
    if [ -z "$REPLY" ]; then
        menu
        continue
    fi
    for x in $REPLY; do _$x || break; done;
done

