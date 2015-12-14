# Promotion Bias
Inspired by [Male-female differences: A computer simulation] [1], this Java app simulates how gender bias results in larger than expected disparity at the top levels of an organization.

# Running the Code

Use Gradle from the command line:

`./gradlew run`

Or load it up in your IDE of choice and run the `main` method in the `bias.Simulation` class. Here is typical output:

<pre>
Promotion Bias  : 2
Attrition Rate  : 15
Promotion Cycles: 20
Promotion Model : TOURNAMENT
Attrition Model : RANDOM
Simulations     : 20

Level  Men            Women
-----  -------------  -------------
    0  240.8 (48.2%)  259.2 (51.8%)
    1  171.1 (48.9%)  178.9 (51.1%)
    2  100.4 (50.2%)   99.6 (49.8%)
    3   77.4 (51.6%)   72.6 (48.4%)
    4   52.6 (52.6%)   47.4 (47.4%)
    5   41.1 (54.8%)   33.9 (45.2%)
    6   23.0 (57.5%)   17.0 (42.6%)
    7    6.1 (60.9%)    3.9 (39.2%)
</pre>

# How it Works

The simulation starts with an 8-level organization with a pyramid structure, in which there are fewer positions as you move toward the top. There are 500 entry level positions, 350 at the next level up, on up to only 10 positions at the "executive" level. The organization is seeded with 50% men and 50% women at each level.

We then go through 20 promotion cycles:

1. Randomly remove 15% of employees at every level.
2. Assign a random performance rating to each employee. Women's scores are in the range 0..99, and men's scores are in the range 0..[99 + bias]
3. Promote the highest scoring individuals to the next level up.
4. Hire an equal number of men and women into the lowest level of the organization.

This entire simulation is then repeated 20 times to average out differences.

[1]: http://psycnet.apa.org/psycinfo/1996-02655-011 "Male-female differences: A computer simulation. Martell, Richard F.; Lane, David M.; Emrich, Cynthia. American Psychologist, Vol 51(2), Feb 1996, 157-158."

