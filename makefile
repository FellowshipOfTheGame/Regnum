java-src = $(shell find src -name '*.java')
BUILD_DIR = build
libs = lib/jogl-all.jar
pastas = Controle Modelo org Visual
copia-src = img/ font/

all: pasta $(java-src)
	javac -cp $(libs) -d $(BUILD_DIR) $(java-src)

pasta:
	@mkdir -p $(BUILD_DIR)

release:
	jar cfm regnum.jar manifest.txt $(foreach p,$(pastas),-C build $p) $(libs)
	zip -r release.zip regnum.jar $(copia-src)

