import numpy as np
import datetime
import random as rand
import matplotlib.pyplot as plt

class BehavioralCloning:

    @staticmethod
    def affine_forward (a, weight, b):
        Z = a @ weight + b
        cache = (np.copy(a), np.copy(weight), np.copy(b))
        return Z, cache


    @staticmethod
    def affine_backward (dz, cache):
        forward_a, forward_w, forward_B = cache
        da = dz @ forward_w.T
        dw = forward_a.T @ dz
        db = np.zeros(dz.shape[1])
        for i in range (db.shape[0]):
            db[i] = np.sum(dz[:, i])
        return (da, dw, db)


    @staticmethod
    def ReLUForward(Z):
        dz = np.copy(Z)
        for i in range(len(Z)):
            for j in range(len(Z[0])):
                if dz[i][j] < 0:
                    dz[i][j] = 0
        return dz, np.copy(Z)

    @staticmethod
    def ReLUBack(dA, cache):
        A = np.copy(dA)
        for i in range(len(cache)):
            for j in range(len(cache[0])):
                if cache[i][j] <= 0:
                    A[i][j] = 0
        return A

    @staticmethod
    def CrossEnt(F, y):
        batch_size = F.shape[0]
        number_of_action = F.shape[1]
        total_sum = 0.0

        for i in range(0, batch_size):
            sum = 0.0
            for j in range(0, number_of_action):
                sum += np.exp(F[i][j])
            log_sum = np.log(sum)
            total_sum += F[i][int(y[i])] - log_sum

        dF = np.copy(F)
        for i in range(dF.shape[0]):
            for j in range(dF.shape[1]):
                sum = 0.0
                for z in range(0, number_of_action):
                    sum += np.exp(F[i][z])
                exp = np.exp(F[i][j]) / sum

                if j == y[i]:
                    dF[i][j] = 1 - exp
                else:
                    dF[i][j] = -exp

        return -(total_sum / batch_size), ((-1/batch_size) * dF)


def get_rand_weights():
    w1 = np.zeros((5, 256))
    w2 = np.zeros((256, 256))
    w3 = np.zeros((256, 3))

    for i in range(w1.shape[0]):
        for j in range(w1.shape[1]):
            w1[i][j] = rand.uniform(-1, 1)/1.1

    for i in range(w2.shape[0]):
        for j in range(w2.shape[1]):
            w2[i][j] = rand.uniform(-1, 1)/1.1

    for i in range(w3.shape[0]):
        for j in range(w3.shape[1]):
            w3[i][j] = rand.uniform(-1, 1)/1.1
    return w1, w2, w3


batchsize = 64
loss = []
examplesize = 10000
behavior_cloning = BehavioralCloning()
learning_rate = 0.1
accuracies = []
confusion_matrix = np.zeros((3, 3))
consecutive = []

def minibatch(data, epoch):

    global learning_rate
    global loss
    global accuracies
    global confusion_matrix

    weight1, weight2, weight3 = get_rand_weights()
    b1 = np.zeros(256)
    b2 = np.zeros(256)
    b3 = np.zeros(3)
    b1.fill(0)
    b3.fill(0)
    b1.fill(0)
    for e in range(epoch):
        rand.seed(datetime.datetime.now())
        np.random.shuffle(data)
        N = examplesize
        example = data[:examplesize]
        loss_list = []

        for i in range(0, N, batchsize):
            batch = example[i: i + batchsize]
            X = batch[:, :5]
            y = batch[:, 5]

            l, (w1, w2, w3), (nb1, nb2, nb3) = three_network(X, weight1, weight2, weight3, b1, b2, b3, y, False)
            loss_list.append(l)
            weight1, weight2, weight3 = (w1, w2, w3)
            b1, b2, b3 = (nb1, nb2, nb3)

        #for loss vs epoch graph graph
        loss.append(np.sum(loss_list)/len(loss_list))

        print(e, loss[e])
        if(e < 50):
            learning_rate = learning_rate - 0.0001
        else:
            learning_rate = learning_rate * .97
        cur_class = (three_network(data[:, :data.shape[1]-1], weight1, weight2, weight3,
                                  b1, b2, b3, data[:, data.shape[1]-1], True))
        cur_true_data = data[:, data.shape[1] - 1]
        cur_count = 0
        for i in range(len(cur_class)):
            if cur_class[i] == cur_true_data[i]:
                cur_count += 1
        accuracies.append(cur_count/len(cur_class))

    global_class = (three_network(data[:, :data.shape[1]-1], weight1, weight2, weight3,
                                  b1, b2, b3, data[:, data.shape[1]-1], True))

    true_data = data[:, data.shape[1]-1]

    print('global classes')
    for i in range(len(global_class)):
        print('our: ' + str(global_class[i]) + ', actual: ' + str(true_data[i]))

    count = 0
    c = 0
    for i in range(len(global_class)):
        if global_class[i] == true_data[i]:
            count += 1
            confusion_matrix[int(true_data[i])][int(global_class[i])] += 1
            c += 1
        else:
            consecutive.append(c)
            c = 0
            confusion_matrix[int(true_data[i])][int(global_class[i])] += 1
            print(expert_data[i], global_class[i])

    for i in range (3):
        confusion_matrix[i] = confusion_matrix[i] / np.sum(confusion_matrix[i])

    print(count/len(global_class))

    return weight1, weight2, weight3, b1, b2, b3


def three_network(X, w1, w2, w3, b1, b2, b3, y, test):
    Z1, acache1 = behavior_cloning.affine_forward(X, w1, b1)
    A1, racache1 = behavior_cloning.ReLUForward(Z1)
    Z2, acache2 = behavior_cloning.affine_forward(A1, w2, b2)
    A2, racache2 = behavior_cloning.ReLUForward(Z2)
    F, acache3 = behavior_cloning.affine_forward(A2, w3, b3)
    if test:
        classifications = np.zeros(F.shape[0])
        for i in range(len(classifications)):
            classifications[i] = np.argmax(F[i])
        return classifications

    loss, dF = behavior_cloning.CrossEnt(F, y)
    dA2, dw3, db3 = behavior_cloning.affine_backward(dF, acache3)
    dZ2 = behavior_cloning.ReLUBack(dA2, racache2)
    dA1, dw2, db2 = behavior_cloning.affine_backward(dZ2, acache2)
    dZ1 = behavior_cloning.ReLUBack(dA1, racache1)
    dX, dw1, db1 = behavior_cloning.affine_backward(dZ1, acache1)

    w1 = w1 - learning_rate * dw1
    w2 = w2 - learning_rate * dw2
    w3 = w3 - learning_rate * dw3

    b1 = b1 - learning_rate * db1
    b2 = b2 - learning_rate * db2
    b3 = b3 - learning_rate * db3

    return loss, (w1, w2, w3), (b1, b2, b3)


f = open("expertdata.txt", "r")

expert_data = np.zeros((10000, 6))
row = 0

while row < 10000:
    line = f.readline().replace('\n', '')
    contents = line.split(" ")
    float_list = [float(x) for x in contents]
    for i in range(len(contents)):
        expert_data[row][i] = float_list[i]
    row += 1

# for i in range(5):
#     expert_data[:, i] = (expert_data[:, i] - np.mean(expert_data[:, i])) / np.std(expert_data[:, i])

epoch = 150

# minibatch(expert_data, epoch)
# y = np.arange(1, epoch+1, 1)
#
# plt.figure(1)
# plt.subplot(211)
# plt.title('Behavioral Cloning')
# plt.xlabel('epoch')
# plt.ylabel('loss')
# plt.plot(y, loss)
# plt.show()
#
# plt.subplot(212)
# plt.title('Behavioral Cloning')
# plt.xlabel('epoch')
# plt.ylabel('accuracy')
# plt.plot(y, accuracies)
# plt.show()
#
# print('confusion matrix: ')
# print(confusion_matrix)
# print('')
# print('average hit ' + str(np.sum(consecutive) / len(consecutive)))

print(np.max(expert_data[:, 4]))
print(np.min(expert_data[:, 4]))