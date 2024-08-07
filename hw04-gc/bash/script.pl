while(<>){
    m/\[(\d+,\d+)s\]/g;
    print "$1\n";
}
