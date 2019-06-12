import numpy as np
import pyalgs.data_structures.commons.queue as Queue
import random as rand

class QLearningGUI:
    #initialization
    q_table = None
    visited = None
    alpha = 0.2
    gamma = 0.8
    path = Queue.LinkedListQueue()
    c = 20
    x_pos = 6
    y_pos = 6
    x_vel = 1
    y_vel = 1
    pad_pos = 5
    exp_num = 5

    def update(self, x_pos, y_pos, x_vel, y_vel, pad_pos, action):
        self.set_values(x_pos, y_pos, x_vel, y_vel, pad_pos)
        self.update_path_visited(x_pos, y_pos, x_vel, y_vel, pad_pos, action)

    def set_values(self, x_pos, y_pos, x_vel, y_vel, pad_pos):
        self.x_pos = x_pos
        self.y_pos = y_pos
        self.x_vel = x_vel
        self.y_vel = y_vel
        self.pad_pos = pad_pos

    def make_q_table(self):
        self.q_table = np.zeros(12*12*2*3*12).reshape((12, 12, 2, 3, 12))
        self.visited = np.zeros(12*12*2*3*12*3).reshape((12, 12, 2, 3, 12, 3))

    def update_path_visited(self, x_pos, y_pos, x_vel, y_vel, pad_pos, action):
        self.path.enqueue((x_pos, y_pos, x_vel, y_vel, pad_pos))
        self.visited[x_pos][y_pos][x_vel][y_vel][pad_pos][action] += 1

    def q_table_update(self, t_state, action):
        x, y, dx, dy, p = 0, 0, 0, 0, 0
        while not self.path.is_empty():
            x, y, dx, dy, p = self.path.dequeue()
            if self.path.is_empty():
                break
            nx, ny, ndx, ndy, np = self.path.peek().value
            self.q_table[x][y][dx][dy][p] = self.q_table[x][y][dx][dy][p] + self.alpha\
                                            * (self.c/(self.c + self.visited[x][y][dx][dy][p][action]))\
                                            * ((self.gamma*self.q_table[nx][ny][ndx][ndy][np])
                                               - self.q_table[x][y][dx][dy][p])
        self.q_table[x][y][dx][dy][p] = self.q_table[x][y][dx][dy][p] + self.alpha\
                                        * (self.c / (self.c + self.visited[x][y][dx][dy][p][action]))\
                                        * ((self.gamma * t_state) - self.q_table[x][y][dx][dy][p])

    def path_init(self):
        self.path = Queue.LinkedListQueue()

    def pos_init(self):
        self.x_pos = 6
        self.y_pos = 6
        self.x_vel = 1
        self.y_vel = 1
        self.pad_pos = 5

    def move_pad(self, learned):
        idx = np.argmax(self.q_table[self.x_pos][self.y_pos][self.x_vel][self.y_vel])
        ret = -1
        if self.pad_pos > idx:
            ret = 0
        elif self.pad_pos < idx:
            ret = 2
        else:
            ret = 1

        if learned:
            return ret
        else:
            if self.visited[self.x_pos][self.y_pos][self.x_vel][self.y_vel][self.pad_pos][ret] < self.exp_num:
                val = rand.randint(0, 2)
                while val != ret:
                    val = rand.randint(0, 2)
                return val

            return ret

    def get_q_val(self):
        return np.sum(self.q_table)

    def get_q_table(self):
        return self.q_table.copy()
