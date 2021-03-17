BUILD=herodotusutils
VERSION=1.0

FILE_NAME="$BUILD-$VERSION.jar"
FILE_NAME_DEV="$BUILD-$VERSION-build${GITHUB_RUN_NUMBER}.jar"

mv "$GITHUB_WORKSPACE/HerodotusUtils/build/libs/$FILE_NAME" "$GITHUB_WORKSPACE/HerodotusUtils/HerodotusUtils/artifacts/$FILE_NAME_DEV"
