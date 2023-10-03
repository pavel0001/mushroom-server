#!/bin/zsh
echo 'Start building app, after that it will send to server'
cd ~/idea-projects/ktor-sample || exit
./gradlew buildFatJar
cd ~/idea-projects/ktor-sample/build/libs || exit
rsync -avzhe ssh mushroom_v_0.0.4.jar root@185.105.88.49:/home/mushroom/