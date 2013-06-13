import re
import sys
import string

args = " ".join(sys.argv).partition("'")[2]
args = args.rpartition("'")

regex = re.compile(args[0])
file = open(args[2][1:], 'r')

res = regex.search(file.read())

print ("" if res == None else res.group(0))