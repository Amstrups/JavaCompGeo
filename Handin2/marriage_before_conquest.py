import random
from scipy.optimize import linprog
import math 
import matplotlib.pyplot as plt
import numpy as np
import generate_set
import sys
import time


a = [(1,0),(2,8),(3,3),(4,1)]
# a = generate_set.generate_circle(10, 100)
x = []
y = []
for elem in a:
    x.append(elem[0])
    y.append(elem[1])

class MarriageBeforeConquest: 
    def __init__(self):
        self.result = set()
    
    def show_plot(self, a):
        def f(x):
            return x[0]
        
        x = []
        y = []
        x_res = []
        y_res = []
        for elem in a:
            x.append(elem[0])
            y.append(elem[1])
        plt.plot(x,y,'o')

        X = list(self.result)
        X.sort(key=f)
        for elem in X:
            x_res.append(elem[0])
            y_res.append(elem[1])
        plt.plot(x,y,'o')
        plt.plot(x_res, y_res)
        # print("Result: ", M.result)
        plt.show()


        
    def partition(self,a):
        if (len(a) < 1):
            print(len(a))
            return a[0], [], a, [-a[1]]
        
        # pivot_index = random.randint(0,len(a)-1)
        # pivot = a[pivot_index][0]
        
        total = 0
        for elem in a:
            total += elem[0]
        pivot = total/len(a)
        # print("Pivot: ", a[pivot_index])
        left, right = [],[]
        constraints = []
        for elem in a:
            if (elem[0] < pivot):
                left.append(elem)
            else:
                right.append(elem)
            constraints.append(-elem[1])
        return pivot,left,right,constraints

    def prune(self,a,lb,ub):
        return_set = []
        for i,elem in enumerate(a):
            if (elem[0] < lb or elem[0] > ub):
                return_set.append(elem)
        return return_set

    def conquest(self,a,last=None):
        pivot,left,right,constraints = self.partition(a)  
        # print("Pivot: ", pivot)
        # print("a: ", a)  
        # print("left: ", left, " right: ", right)
        des = [pivot, 1]
        cons = []
        for elem in a:
            cons.append([-elem[0],-1])

        points = []
        if last is not None:
            cons.append([-last[0],-1])
            points.append(last)
            constraints.append(-last[1])
            
        x = linprog(des, A_ub=cons, b_ub=constraints, bounds=[-np.inf, np.inf])

        # print("x.x: ", x.x)
        for elem in a:
            test = round(elem[0]*x.x[0]+x.x[1])
            # print("Testing: ", elem[1], " vs: ", round(test,3))
            if (round(elem[0]*x.x[0]+x.x[1]) == elem[1]):
                points.append(elem)
                # print("Found new: ", elem, " vs: ", test)
                self.result.add(elem)
                # print("Adding: ", elem)
        # print("Points: ", points)
        # print("Current res: ", self.result)
        l,r = 0,1
        # try:
        # print(points)
        if (points[0][0] > points[1][0]):
            l,r = 1,0
                
        lb,ub = points[l][0], points[r][0]
        # print("Left before: ", len(left))
        # print("Right before: ", len(right))
        left = self.prune(left, lb,ub)
        right = self.prune(right, lb,ub)
        # left.append(points[l])
        # right.append(points[r])
        # print("Left: ", left)
        # print("Right: ", right)
        # print("Left after: ", len(left))
        # print("Right after: ", len(right))
        if (len(left) == 1):
            self.result.add(left[0])
        if (len(right) == 1):
            self.result.add(right[0])
            return 
        if (len(left) > 1): 
            self.conquest(left, points[l])
        if (len(right) > 1): 
            self.conquest(right, points[r])
        return
# a = generate_set.generate_square(100, 10000,10000)
# sys.setrecursionlimit(10000)
# a = [(93.18, 22.0), (86.75, 38.9), (65.68, 2.16), (93.58, 23.03), (47.62, 10.15), (12.51, 53.83), (31.01, 9.29), (81.99, 58.59)]
# a = [(1082, 5657), (8126, 7810), (2943, 8992), (3207, 914), (9112, 6687), (1703, 4923), (3405, 8203), (6080, 4039), (6678, 8809), (9374, 3786)]
# print(a)
iterations = 6
overall = {}

# Warm up
# t = 0
# for j in range(1,iterations+1):
#     n = int(math.pow(10,j))
#     result = []
#     m = 20
#     if (j == 1):
#         m += 1
#     for i in range(m): 
#         a = generate_set.generate_negative_curve(n, 10000)
#         start = time.time_ns()
#         M = MarriageBeforeConquest()
#         M.conquest(a)
#         end = time.time_ns()
#         result.append(end-start)
#         # M.show_plot(a)
#     if (j==1):
#         del result[0]
#     print(f"Run {j} done")
#     overall[f"{n}"] = result
#     total = 0
#     for k in result:
#         total += k
#     overall[f"{n}_average"] = total/len(result)
# print(overall)
