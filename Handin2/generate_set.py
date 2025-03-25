import random
import math
import matplotlib.pyplot as plt
import numpy as np

def generate_square(n, x_ub, y_ub):
    return_set = set()
    for _ in range(n):
        x = random.randint(0,x_ub)
        y = random.randint(0,y_ub)
        return_set.add((x,y))
    return list(return_set)

def generate_circle(n, x_ub):
    return_set = set()
    r = x_ub/2
    for _ in range(n):
        x = random.randint(0,x_ub)
        diff = math.sqrt(math.pow(r,2)-math.pow(abs(r-x), 2))
        y = random.randint(math.ceil((r-diff)), math.floor((r+diff)))
        return_set.add((x,y))
    return list(return_set)

def generate_positive_curve(n, x_ub):
    return_set = set()
    for _ in range(n):
        x = random.randint(0,x_ub) 
        return_set.add((x, math.pow(x,2)))
    return list(return_set)

def generate_negative_curve(n, x_ub):
    return_set = set()
    for _ in range(n):
        x = random.randint(0,x_ub) 
        return_set.add((x, -math.pow(x,2)))
    return list(return_set)

def list_to_coor(a):
    x,y = [],[]
    for elem in a:
        x.append(elem[0])
        y.append(elem[1])
    return [x,y]

# a = generate_circle(10000, 10)
# [x,y] = list_to_coor(a)
# fig = plt.figure()

# plt.plot(x,y, 'o', )
# plt.xlim(0,10)
# plt.ylim(0,10)

# def xy(r,phi):
#   return r*np.cos(phi)+5, r*np.sin(phi)+5

# phis=np.arange(0,6.28,0.01)
# r =5.
# plt.plot( *xy(r,phis), c='r',ls='-' )

# plt.show()