import re
import sys
import string

args = sys.argv
file = open(args[1], 'r+')
print('Patching '+args[1])

text = file.read()

text = re.sub(r'public WorldServer', 'public WorldServer(MinecraftServer minecraftServer, ISaveHandler saveHandler, String par2String, WorldProvider provider, WorldSettings par4WorldSettings, Profiler theProfiler, ILogAgent worldLogAgent)\n\t{\n\t\tsuper(saveHandler, par2String, provider, par4WorldSettings, theProfiler, worldLogAgent);\n\t\tthis.mcServer = minecraftServer;\n\t\tthis.theEntityTracker = null;\n\t\tthis.thePlayerManager = null;\n\t\tthis.field_85177_Q = null;\n\t}\n\n\tpublic WorldServer', text, 1)

file.seek(0)
file.truncate()
file.flush()
file.seek(0)
file.write(text)

file.flush()
file.close()