'''
https://github.com/mattalexpugh/beginner-python-games/blob/master/pong-gui.py
Pong GUI referred to the above link (Authored by Matt Pugh).
'''
#!/usr/bin/python

from tkinter import *
import random
import numpy as np
import QLearning

def move_ball():
    canvas.move(ball, x_velocity, y_velocity)


def move_computer(direction):
    comp_pos = canvas.coords(computer)

    #down
    if direction == 2:
        if comp_pos[3] + PADDLE_VELOCITY <= SIZE:
            canvas.move(computer, 0, PADDLE_VELOCITY)
        else:
            canvas.move(computer, 0, SIZE-comp_pos[3])
    #up
    elif direction == 0:
        if comp_pos[1] - PADDLE_VELOCITY >= 0:
            canvas.move(computer, 0, -PADDLE_VELOCITY)
        else:
            canvas.move(computer, 0, -comp_pos[1])


def display_max_hit():
    global canvas
    global max_hit_count
    global hit_label

    canvas.delete(hit_label)
    hit_label = canvas.create_text(240, 40, text='max hit is : ' + str(max_hit_count), fill='black', font="Times 20 bold")


def bounce_ball():
    global x_velocity
    global y_velocity
    left_wall_coords = canvas.coords(left_wall)
    top_wall_coords = canvas.coords(top_wall)
    bot_wall_coords = canvas.coords(bot_wall)
    paddle_coords = canvas.coords(computer)

    flip_x = random.randint(0, 1) * 1
    flip_y = random.randint(0, 1) * 1
    U = random.uniform(0, 1) * 7.2
    V = random.uniform(0, 1) * 14.4
    if flip_x == 1:
        U = -U
    if flip_y == 1:
        V = -V

    ball_coords = canvas.coords(ball)

    if ball_coords[1] < top_wall_coords[3] or ball_coords[3] > bot_wall_coords[1]:
        if ball_coords[1] < top_wall_coords[3]:
            ball_coords[1] = top_wall_coords[3]
        elif ball_coords[3] > bot_wall_coords[1]:
            ball_coords[3] = bot_wall_coords[1]

        y_velocity = -y_velocity

    if ball_coords[0] < left_wall_coords[2]:
        ball_coords[0] = left_wall_coords[2]
        x_velocity = -x_velocity

    elif ball_coords[2] > paddle_coords[0]:
        ball_loc = (ball_coords[1] + ball_coords[3])/2
        pad_loc = (paddle_coords[1] + paddle_coords[3])/2
        if (ball_loc > pad_loc - 48) and (ball_loc < pad_loc + 48):
            ball_coords[2] = paddle_coords[0]
            x_velocity = -x_velocity + U
            y_velocity = -y_velocity + V

    if abs(x_velocity) < 14.4:
        if x_velocity < 0:
            x_velocity = -14.4
        else:
            x_velocity = 14.4

    if abs(x_velocity) >= SIZE:
        x_velocity = 0.8 * x_velocity
    if abs(y_velocity) >= SIZE:
        y_velocity = 0.8 * y_velocity
    #print('x_vel :' + str(x_velocity))
    #print('y_vel :' + str(y_velocity))


def reset_computer():
    global computer
    canvas.delete(computer)
    computer = canvas.create_rectangle((SIZE - 10, 240, SIZE, 144), fill="black")

def reset_ball():
    global canvas
    global ball
    global x_velocity
    global y_velocity

    x_velocity = 14.4
    y_velocity = 4.8
    canvas.delete(ball)
    ball = canvas.create_oval((245, 245, 255, 255), fill="red")


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


def get_ball_loc():
    global ball

    ball_coords = canvas.coords(ball)
    ball_x_loc = (ball_coords[0] + ball_coords[2]) / 2
    ball_y_loc = (ball_coords[1] + ball_coords[3]) / 2
    x_loc = int(ball_x_loc / (SIZE / 12)) - 1
    y_loc = int(ball_y_loc / (SIZE / 12)) - 1

    if x_loc >= 12:
        x_loc = 11
    elif x_loc < 0:
        x_loc = 0

    if y_loc >= 12:
        y_loc = 11
    elif y_loc < 0:
        y_loc = 0

    return x_loc, y_loc


def get_pad_loc():
    global computer
    paddle_coords = canvas.coords(computer)
    pad_pos = (paddle_coords[1] + paddle_coords[3]) / 2
    pad_loc = int(pad_pos/(SIZE/12))-1

    return pad_loc


def refresh():
    global ball
    global computer
    global x_velocity
    global y_velocity
    global count
    global hit_count
    global test_games
    global max_hit_count

    display_max_hit()

    cur_x_vel, cur_y_vel = get_cur_velocity()
    ball_x_loc, ball_y_loc = get_ball_loc()
    pad_pos = get_pad_loc()
    idx = np.argmax(q_table[ball_y_loc][ball_x_loc][cur_y_vel][cur_x_vel][pad_pos])
    direction = idx

    move_ball()
    move_computer(direction)

    ball_coords = canvas.coords(ball)
    paddle_coords = canvas.coords(computer)
    left_wall_coords = canvas.coords(left_wall)
    top_wall_coords = canvas.coords(top_wall)
    bot_wall_coords = canvas.coords(bot_wall)

    if ball_coords[0] > SIZE:
        reset_ball()
        reset_computer()
        hit_count = 0
        test_games += 1

    if ball_coords[0] <= left_wall_coords[2]:
        bounce_ball()

    elif ball_coords[2] >= paddle_coords[0]:
        ball_loc = (ball_coords[1] + ball_coords[3]) / 2
        if ball_loc >= paddle_coords[1] and ball_loc <= paddle_coords[3]:
            bounce_ball()
            hit_count += 1

    elif ball_coords[1] <= top_wall_coords[3] or ball_coords[3] >= bot_wall_coords[1]:
        bounce_ball()

    if hit_count > max_hit_count:
        max_hit_count = hit_count
        print(max_hit_count)
    if test_games >= 200:
        print('max hit count is: ' + str(max_hit_count))
        return

    tk_obj.after(REFRESH_TIME, refresh)


PADDLE_VELOCITY = 19.2
REFRESH_TIME = 40
SIZE = 480
tk_obj = Tk()

canvas = Canvas(tk_obj, background="white", width=SIZE, height=SIZE)

left_wall = canvas.create_rectangle((0, 0, 10, SIZE), fill="black")
top_wall = canvas.create_rectangle((0, 0, SIZE, 10), fill="black")
bot_wall = canvas.create_rectangle((0, SIZE-10, SIZE, SIZE), fill="black")
computer = canvas.create_rectangle((SIZE-10, 240, SIZE, 144), fill="black")

ball = None
hit_label = None
x_velocity = 14.4
y_velocity = 4.8

count = 0
hit_count = 0
max_hit_count = 0
test_games = 0
converge_count = 0
learned = False

q_table = QLearning.get_table()
for i in range (12):
    for j in range(12):
        print(q_table[i][j])

canvas.pack()
reset_ball()
tk_obj.after(REFRESH_TIME, refresh)
tk_obj.mainloop()