#!/bin/zsh
echo 'Start building app, after that will run localhost server'
./gradlew build
cd ~/idea-projects/ktor-sample || exit
python -m http.server 8080