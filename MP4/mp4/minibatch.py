import numpy as np

batchsize = 100
loss = []

def minibatch(data, epoch, action):
    weight1 = np.random.rand(5, 256)
    weight2 = np.random.rand(256, 256)
    weight3 = np.random.rand(256, 5)

    for e in range(epoch):
        np.random.shuffle(data)
        N = len(data)
        batch = np.zeros((batchsize, 6))
        for i in range(int(N/batchsize)):
            batch = data[:100]
            X = batch[:][:5]
            y = batch[:][6]
            loss.append(three_network(X, weight1, weight2, weight3, y, action))

def three_network(X, w1, w2, w3, y, action):
    return 1


f = open("expertdata.txt", "r")

expert_data = np.zeros((10000, 6))
row = 0
while(row < 10000):
    line = f.readline().replace('\n', '')
    contents = line.split(" ")
    float_list = [float(x) for x in contents]
    for i in range(len(contents)):
        expert_data[row][i] = float_list[i]
    row += 1

minibatch(expert_data, 3, 1)