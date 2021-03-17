BUILD=herodotusutils
VERSION=1.0
DEV=dev

FILE_NAME="$BUILD-$VERSION.jar"
FILE_NAME_DEV="$BUILD-$VERSION-build${GITHUB_RUN_NUMBER}.jar"

mv /home/runner/work/HerodotusUtils/HerodotusUtils/build/libs/$FILE_NAME /home/runner/work/HerodotusUtils/HerodotusUtils/artifacts/
mv /home/runner/work/HerodotusUtils/HerodotusUtils/artifacts/$FILE_NAME /home/runner/work/HerodotusUtils/HerodotusUtils/artifacts/$FILE_NAME_DEV
