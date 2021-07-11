file1 = open('lansstyrelsen_extract.txt', 'r')
Lines = file1.readlines()

print(Lines)
cutLines = []
for line in Lines:
    cutLines.append(line.split(','))
    

dataFile = open('integer_data.txt', 'w')

for line in cutLines:
    print("Each line in array: ", line[10])
    dataFile.write("INSERT INTO usertable(costm2_SEK) VALUES(" + (line[10]) + ");")
    dataFile.write("\n")
file1.close()
dataFile.close()