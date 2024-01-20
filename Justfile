set shell := ["nu", "-c"]

alias b := build

build: && zip
    javac MP1.java

br: build run

run TEST='1': build
    cat input.txt | java MP1 {{TEST}}

zip:
    ^zip MP1.zip MP1.java
