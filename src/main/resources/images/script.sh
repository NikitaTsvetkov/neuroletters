for img in $(find . -iname '*.png'); do echo -n "Converting $img"; convert -colorspace GRAY $img $img && echo ' [Done]'; done
