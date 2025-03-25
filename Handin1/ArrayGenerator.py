import random

# Create a list of unique numbers
unique_numbers = list(range(1, 1000001))

# Shuffle the list to randomize the order
random.shuffle(unique_numbers)

# Convert the numbers to strings
array = [str(num) for num in unique_numbers]

# Join the numbers with commas
array_str = ",".join(array)

# Save the array to a file
with open("array.txt", "w") as file:
    file.write(array_str)
