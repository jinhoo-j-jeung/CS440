import datetime

import numpy as np
import random as rand
import copy as copy
import matplotlib.pyplot as plt

Qtable = None
visited = None
gamma = 0.95

c = 50
epsilon = 0.015

SIZE = 480
PADDLE_VELOCITY = SIZE * 0.04
y_velocity = SIZE * 0.03
x_velocity = SIZE * 0.015
x_pos = SIZE * 0.5
y_pos = SIZE * 0.5
pad_x_pos = SIZE * 0.4
prev_q_val = 0

def init():
    global x_velocity
    global y_velocity
    global x_pos
    global y_pos
    global pad_x_pos

    y_velocity = 14.4
    x_velocity = 4.8
    x_pos = 240
    y_pos = 240
    pad_x_pos = 192


def makeQtable():
    global visited
    global Qtable
    Qtable = np.zeros(12*12*2*3*12*3).reshape((12, 12, 2, 3, 12, 3))
    visited = np.zeros(12*12*2*3*12*3).reshape((12, 12, 2, 3, 12, 3))



def Qtableupdate(reward, prev_action, action, prev_state, cur_state, terminate):
    global Qtable
    x, y, dx, dy, p = cur_state
    px, py, pdx, pdy, pp = prev_state

    if visited[px][py][pdx][pdy][pp][prev_action] == 0:
        Qtable[px][py][pdx][pdy][pp][prev_action] = reward
    else:
        Qtable[px][py][pdx][pdy][pp][prev_action] = Qtable[px][py][pdx][pdy][pp][prev_action]\
                                                    + ((c/(c+visited[px][py][pdx][pdy][pp][prev_action])))\
                                  * (gamma*Qtable[x][y][dx][dy][p][action] - Qtable[px][py][pdx][pdy][pp][prev_action])

    if terminate:
        Qtable[x][y][dx][dy][p][action] = Qtable[x][y][dx][dy][p][action] + (c/(c+visited[x][y][dx][dy][p][action]))\
                                  * (reward - Qtable[x][y][dx][dy][p][action])


# def Qtableupdate(reward, prev_action, action, prev_state, cur_state, terminate):
#     global Qtable
#     x, y, dx, dy, p = cur_state
#     px, py, pdx, pdy, pp = prev_state
#
#     if visited[px][py][pdx][pdy][pp][prev_action] == 0:
#         Qtable[px][py][pdx][pdy][pp][prev_action] = reward
#     else:
#         Qtable[px][py][pdx][pdy][pp][prev_action] = Qtable[px][py][pdx][pdy][pp][prev_action]\
#                                                     + ((c/(c+visited[px][py][pdx][pdy][pp][prev_action])))\
#                                   * (gamma*Qtable[x][y][dx][dy][p][action] - Qtable[px][py][pdx][pdy][pp][prev_action])
#
#     if terminate:
#         Qtable[x][y][dx][dy][p][action] = Qtable[x][y][dx][dy][p][action] + (c/(c+visited[x][y][dx][dy][p][action]))\
#                                   * (reward - Qtable[x][y][dx][dy][p][action])


def Qtableupdate(reward, prev_action, action, prev_state, cur_state, terminate):
    global Qtable
    x, y, dx, dy, p = cur_state
    px, py, pdx, pdy, pp = prev_state

    if visited[px][py][pdx][pdy][pp][prev_action] == 0:
        Qtable[px][py][pdx][pdy][pp][prev_action] = reward
    else:
        max_val = np.amax(Qtable[x][y][dx][dy][p])
        Qtable[px][py][pdx][pdy][pp][prev_action] = Qtable[px][py][pdx][pdy][pp][prev_action] + ((c/(c+visited[px][py][pdx][pdy][pp][prev_action])))\
                                  * (gamma*max_val - Qtable[px][py][pdx][pdy][pp][prev_action])
    if terminate:
        Qtable[x][y][dx][dy][p][action] = Qtable[x][y][dx][dy][p][action] + (c/(c+visited[x][y][dx][dy][p][action]))\
                                  * (reward - Qtable[x][y][dx][dy][p][action])


def bounce_ball():
    global x_velocity
    global y_velocity
    global x_pos
    global y_pos
    global pad_x_pos

    rand.seed(datetime.datetime.now())
    flip_x = rand.randint(0, 1) * 1
    flip_y = rand.randint(0, 1) * 1
    U = rand.uniform(0, 1) * 7.2
    V = rand.uniform(0, 1) * 14.4
    if flip_x == 1:
        U = -U
    if flip_y == 1:
        V = -V

    if x_pos < 5 or x_pos > SIZE-5:
        if x_pos < 5:
            x_pos = 5
        elif x_pos > SIZE-5:
            x_pos = SIZE-5

        x_velocity = -x_velocity

    if y_pos < 5:
        y_pos = 5
        y_velocity = -y_velocity

    elif y_pos > SIZE-5:
        if (x_pos > pad_x_pos - 48) and (x_pos < pad_x_pos + 48):
            y_pos = SIZE - 5
            x_velocity = -x_velocity + V
            y_velocity = -y_velocity + U

    if abs(y_velocity) < 14.4:
        if y_velocity < 0:
            y_velocity = -14.4
        else:
            y_velocity = 14.4

    if abs(x_velocity) >= SIZE:
        x_velocity = 0.8 * x_velocity
    if abs(y_velocity) >= SIZE:
        y_velocity = 0.8 * y_velocity


def get_ball_loc():
    global x_pos
    global y_pos

    x_loc = int(x_pos / (SIZE / 12)) - 1
    y_loc = int(y_pos / (SIZE / 12)) - 1

    if x_loc >= 12:
        x_loc = 11
    elif x_loc < 0:
        x_loc = 0

    if y_loc >= 12:
        y_loc = 11
    elif y_loc < 0:
        y_loc = 0

    return x_loc, y_loc


def get_cur_velocity():
    global x_velocity
    global y_velocity

    if x_velocity >= 0:
        cur_x_vel = 1
    else:
        cur_x_vel = -1

    if y_velocity >= 0:
        cur_y_vel = 1
    else:
        cur_y_vel = -1

    return cur_x_vel, cur_y_vel


def get_pad_loc():
    global pad_x_pos

    pad_loc = int(pad_x_pos/(SIZE/12))-1

    return pad_loc


def move_pad(direction):
    global pad_x_pos

    if direction == 2:
        if pad_x_pos + 48 + PADDLE_VELOCITY <= SIZE-5:
            pad_x_pos += PADDLE_VELOCITY
        else:
            pad_x_pos = SIZE - 48
    #up
    elif direction == 0:
        if pad_x_pos - 48 - PADDLE_VELOCITY >= 5:
            pad_x_pos -= PADDLE_VELOCITY
        else:
            pad_x_pos = 48

# when 3rd value is 0 the x_velocity is -1 , otherwise 1
# when 4th value is 0 the y_velocity is -1 ,  1 = 1 , 2 = 0
def get_table():
    global visited
    global Qtable
    global y_pos
    global x_pos
    global pad_x_pos
    global exp_num
    global epsilon

    count = 0
    converge_count = 0
    hit_count = 0
    miss_count = 0
    makeQtable()
    total_hit = 0
    prev_action = 1
    results = []

    while True:
        init()
        dis_x_pos, dis_y_pos = get_ball_loc()
        dis_x_vel, dis_y_vel = get_cur_velocity()
        dis_pad_pos = get_pad_loc()
        #visited[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos][1] += 1
        prev_q_table = Qtable.copy()
        action = 1
        while True:
            prev_action = action
            visited[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos][prev_action] += 1

            updated = False
            prev_state = (copy.deepcopy(dis_x_pos), copy.deepcopy(dis_y_pos), copy.deepcopy(dis_x_vel),
                          copy.deepcopy(dis_y_vel), copy.deepcopy(dis_pad_pos))

            x_pos += x_velocity
            y_pos += y_velocity

            dis_x_pos, dis_y_pos = get_ball_loc()
            dis_x_vel, dis_y_vel = get_cur_velocity()
            dis_pad_pos = get_pad_loc()
            cur_state = (dis_x_pos, dis_y_pos, dis_x_vel, dis_y_vel, dis_pad_pos)
            action = np.argmax(Qtable[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos])

            #print('dis_pad_pos : ' + str(dis_pad_pos) + ' idx:' + str(idx) + ' ball_x:' + str(dis_x_pos) + ' ball_y:' + str(dis_y_pos))
            #if visited[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos][action] < exp_num:
            if rand.random() < epsilon:
                action = -1
                #print("random")
                for i in range(len(visited[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos])):
                    if(visited[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos][i] == 0):
                        action = i
                        break

                if(action == -1):
                    rand.seed(datetime.datetime.now())
                    val = rand.randint(0, 2)
                    while -1 != action:
                        rand.seed(datetime.datetime.now())
                        val = rand.randint(0, 2)
                    action = val
            #print("after random")
            move_pad(action)

            #top wall
            if x_pos <= 5:
                bounce_ball()

            #left wall
            if y_pos <= 5:
                bounce_ball()

            #out or in
            if y_pos >= SIZE-5:
                updated = True
                if pad_x_pos - 48 <= x_pos <= pad_x_pos + 48:
                    Qtableupdate(1, prev_action, action, prev_state, cur_state, True)
                    hit_count += 1
                    total_hit += 1
                    miss_count = 0
                    bounce_ball()
                else:
                    count += 1
                    visited[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos][action] += 1
                    Qtableupdate(-1, prev_action, action, prev_state, cur_state, True)
                    miss_count += 1
                    hit_count = 0
                    break
            #bottom
            if x_pos >= SIZE-5:
                bounce_ball()


            if not updated:
                Qtableupdate(0, prev_action, action, prev_state, cur_state, False)

        if count == 100000:
            if converge_count >= 3:
                 print('converged')
                 break
            else:
                 if np.allclose(prev_q_table, Qtable, 1.e-6, 0):
                     converge_count += 1
                     print('converging...')
                 else:
                     converge_count = 0

        if count == 10000:
            # for i in range(12):
            #     for j in range(12):
            #         print(Qtable[i][j])
            # print('Over 100000')
            # out_file = open('Qtable', mode='w+')
            # for i in range(12):
            #     for j in range(12):
            #         out_file.write(str(Qtable[i][j]))
            break
        if count % 1000 == 0:
            print('avg : ' + str(total_hit/count) + ', count : ' + str(count))
        results.append(total_hit / count)

    # y = np.arange(1, 100000, 1)
    # plt.title('TD Learning')
    # plt.xlabel('Episodes')
    # plt.ylabel('Mean Reward Epsidoes')
    # plt.plot(y, results)
    # plt.show()

    return Qtable


def test():
    global Qtable
    global y_pos
    global x_pos
    global pad_x_pos
    global exp_num
    get_table()
    count = 0
    hit_count = 0
    max_hit = 0
    test_result = []

    while True:
        init()
        while True:
            x_pos += x_velocity
            y_pos += y_velocity
            dis_x_pos, dis_y_pos = get_ball_loc()
            dis_x_vel, dis_y_vel = get_cur_velocity()
            dis_pad_pos = get_pad_loc()

            # get pad action
            action = np.argmax(Qtable[dis_x_pos][dis_y_pos][dis_x_vel][dis_y_vel][dis_pad_pos])
            move_pad(action)

            #top wall
            if x_pos <= 5:
                bounce_ball()

            #left wall
            if y_pos <= 5:
                bounce_ball()

            #out or in
            if y_pos >= SIZE-5:
                if pad_x_pos - 48 <= x_pos <= pad_x_pos + 48:
                    hit_count += 1
                    bounce_ball()
                else:
                    count += 1
                    test_result.append(hit_count)
                    hit_count = 0
                    break
            #bottom
            if x_pos >= SIZE-5:
                bounce_ball()

            if hit_count > max_hit:
                max_hit = hit_count
        if count == 200:
            print('max hit is: ' + str(max_hit))
            for i in range (200):
                print(test_result[i])
            print('avegrage : ' + str(np.sum(test_result)/200))
            break