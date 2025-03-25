import time
import math
import generate_set
from graham_scan import GrahamScan
from  marriage_before_conquest import MarriageBeforeConquest

iterations = 6
overall = {}

# Warm up
t = 0
for j in range(1,iterations+1):
    n = int(math.pow(10,j))
    result = []
    m = 20
    if (j == 1):
        m += 1
    for i in range(m): 
        # Choose set
        a = generate_set.generate_square(n, 10000, 10000)
        a_ = a.copy()
        start = time.time_ns()

        # Choose approach
        M = GrahamScan(a)
        M.scan(a_)

        end = time.time_ns()
        result.append(end-start)
        # M.show_plot(a)
    if (j==1):
        del result[0]
    print(f"Run {j} done")
    overall[f"{n}"] = result
    total = 0
    for k in result:
        total += k
    overall[f"{n}_average"] = total/len(result)
print(overall)