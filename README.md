# PidManager
An operating system’s pid manager is responsible for managing process identifiers. When a process is first created, it is assigned a unique pid by the pid manager. The pid is returned to the pid manager when the process completes execution, and the manager may later reassign this pid.

#### Highlights: 

- Overall Design: PidManager class was designed as a singleton class because there is only 1 possible Pid Manager in every OS.
- Data Structure: `ConcurrentHashMap<Integer, Integer>` Great Data Structure that allows us to manipulate different processes (allocate/release pids)  in `O(1)` Why concurrent? Simply because all operations are thread-safe. 
