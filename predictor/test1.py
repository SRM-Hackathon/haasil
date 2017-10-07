
import matplotlib.pyplot as plt

data = []
x = []
y = []
y1 = []
#10 Temperature
#11 Humidity
with open('HT_Sensor_dataset.dat', 'r') as f:
    for line in f:
        d = line.split(" ")
        if d[0] == "id":
            continue
        print(d)
        x.append(float(d[2]))
        y.append(float(d[20]))
        y1.append(float(d[22]))
        print(line)

#scatter plot
fig = plt.figure()
ax1 = fig.add_subplot(111)

plt.title('Relationship Between Humidity, Temperature and Time')
ax1.scatter(x, y, s=10, c='b', marker="s", label='Temperature')
ax1.scatter(x, y1, s=10, c='r', marker="o", label='Humidity')
#add x and y labels
plt.xlabel('Time')
plt.ylabel('Temperature in Celcius')

plt.legend(loc='upper left');
plt.show()
