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
    0  248.0 (49.6%)  252.1 (50.4%)
    1  173.2 (49.5%)  176.9 (50.5%)
    2   99.4 (49.7%)  100.7 (50.3%)
    3   74.2 (49.5%)   75.8 (50.5%)
    4   49.5 (49.5%)   50.6 (50.6%)
    5   37.7 (50.2%)   37.4 (49.8%)
    6   21.0 (52.5%)   19.0 (47.5%)
    7    5.6 (55.5%)    4.5 (44.5%)
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

