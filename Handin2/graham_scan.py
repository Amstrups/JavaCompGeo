from enum import Enum
import numpy as np
import generate_set
import matplotlib.pyplot as plt
import math
class InstructionType(Enum):
    ADD = 1
    REMOVE = 2

class Instruction:
    def __init__(self, t, node):
        self.type = t 
        self.node = node
class GrahamScan:
    def __init__(self, a):
        self.p = self.find_p(a)
        self.history = []

    def magnitude(self, x):
        return math.sqrt(math.pow(x[0],2) + math.pow(x[1],2))
    def get_angle(self,x):
        p_ = (10, 0)
        x_ = (x[0]-self.p[0],x[1]-self.p[1])
        r = np.arccos([np.dot(p_,x_)/(self.magnitude(p_)*self.magnitude(x_))])
        return r[0]

    def compare_angle():
        return 

    def sort_vertices(self, a):
        self.a = a.copy()
        self.a.sort(key=self.get_angle)

    def find_p(self, a):
        lowest = a[0]
        index = 0
        for i,elem in enumerate(a[1:]):
            if (elem[1] < lowest[1]):
                lowest = elem
                index = i+1
            elif (elem[1] == lowest[1] and elem[0] < lowest[0]):
                lowest = elem
                index = i+1
        del a[index]
        return lowest
    
    def latest_angles(self):
        [x,y,z] = self.result[-3:]
        return (y[0]-x[0])*(z[1]-x[1]) - (y[1]-x[1])*(z[0]-x[0])

    def backtrack(self):
        if (len(self.result) < 3 ):
            return 

        while len(self.result) > 2:
            if (self.latest_angles() < 0):
                del self.result[len(self.result)-2]
            else:
                return 

    def scan(self, a):
        self.sort_vertices(a)
        self.result = [self.p]
        for elem in self.a:
            self.result.append(elem)
            self.backtrack()
        # history.append[Instruction(InstructionType.ADD, p)]
        self.result.append(self.p)
def list_to_coor(a):
    x,y = [],[]
    for elem in a:
        x.append(elem[0])
        y.append(elem[1])
    return [x,y]

# a = generate_set.generate_negative_curve(10, 100)
# # a_ = a.copy()
# g = GrahamScan(a)

# x = []
# y = []
# x_res = []
# y_res = []
# for elem in a:
#     x.append(elem[0])
#     y.append(elem[1])

# plt.plot(x,y,'o')
# g.scan(a_)
# for elem in g.result:
#     x_res.append(elem[0])
#     y_res.append(elem[1])
# plt.plot(x,y,'o')
# plt.plot(x_res, y_res)
# plt.plot([g.p[0]],[g.p[1]],'o')
# plt.show()