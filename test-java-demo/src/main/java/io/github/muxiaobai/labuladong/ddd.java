package io.github.muxiaobai.labuladong;


import java.util.*;

/**
 * BFS  广度优先 Breadth First Search
 * 最短路径
 * 双向BFS 打开锁的最少步数
 * 1
 * 2  3
 * 4 5 n n
 * 6
 * word-ladder  126 127
 **/
public class ddd {

    public static void main(String[] args) {
//        demo();
        demoWord();
//        System.out.println(reorder(new int[]{8, 3, 4, 2, 5, 7, 6, 1, 0}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0}));
//        System.out.println(equals(new int[]{8, 4, 3, 2, 5, 7, 6, 1, 0}, new int[]{8, 3, 4, 2, 5, 7, 6, 1, 0}));
    }

    static int reorder(int[] begin, int[] end) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> setlist = new ArrayList<>();
        setlist.add(begin);
        queue.offer(begin);
        int depth = 1;
        while (!queue.isEmpty()) {
            depth++;
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                int[] curr = queue.poll();
                List<int[]> like = like(curr);
                for (int k = 0; k < like.size(); k++) {
                    int[] tar = like.get(k);
                    System.out.println(Arrays.toString(curr));
                    System.out.println(Arrays.toString(tar));

                    //已经有的就不要再回查
                    Boolean flag = false;
                    for (int j = 0; j < setlist.size(); j++) {
                        if (equals(setlist.get(j), tar)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        continue;
                    }

                    //和0相等
                    if (equals(end, tar)) {
                        return depth;
                    }
                    queue.offer(tar);
                }
            }
        }
        return 0;
    }

    /**
     * 相等
     */
    static boolean equals(int[] target, int[] curr) {
        int k = 0;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != curr[i]) {
                k++;
            }
        }
        return k == 0;
    }

    /**
     * 找相邻
     *
     * @param curr
     * @return
     */
    static List<int[]> like(int[] curr) {
        List list = new LinkedList();
        int flag = 0;
        for (int i = 0; i < curr.length; i++) {
            if (curr[i] == 0) {
                flag = i;
                break;
            }
        }
        if (flag == 0) {
            list.add(newIntArr(curr, 0, 1));
            list.add(newIntArr(curr, 0, 3));
        } else if (flag == 1) {
            list.add(newIntArr(curr, 1, 0));
            list.add(newIntArr(curr, 1, 2));
            list.add(newIntArr(curr, 1, 4));
        } else if (flag == 2) {
            list.add(newIntArr(curr, 2, 1));
            list.add(newIntArr(curr, 2, 5));
        } else if (flag == 3) {
            list.add(newIntArr(curr, 3, 0));
            list.add(newIntArr(curr, 3, 4));
            list.add(newIntArr(curr, 3, 6));
        } else if (flag == 4) {
            list.add(newIntArr(curr, 4, 1));
            list.add(newIntArr(curr, 4, 3));
            list.add(newIntArr(curr, 4, 5));
            list.add(newIntArr(curr, 4, 7));
        } else if (flag == 5) {
            list.add(newIntArr(curr, 5, 2));
            list.add(newIntArr(curr, 5, 4));
            list.add(newIntArr(curr, 5, 8));
        } else if (flag == 6) {
            list.add(newIntArr(curr, 6, 3));
            list.add(newIntArr(curr, 6, 7));
        } else if (flag == 7) {
            list.add(newIntArr(curr, 7, 4));
            list.add(newIntArr(curr, 7, 6));
            list.add(newIntArr(curr, 7, 8));
        } else if (flag == 8) {
            list.add(newIntArr(curr, 8, 5));
            list.add(newIntArr(curr, 8, 7));
        }
        return list;
    }

    static int[] newIntArr(int[] curr, int currIndex, int targetIndex) {
        int[] newint = new int[9];
        copy(curr, newint);
        newint[currIndex] = newint[targetIndex];
        newint[targetIndex] = 0;
        return newint;
    }

    static void copy(int[] curr, int[] target) {
        for (int i = 0; i < curr.length; i++) {
            target[i] = curr[i];
        }
    }

    static void demoWord() {
        String[] aaa = new String[]{"hot", "dot", "dog", "lot", "log", "cog"};
        ArrayList arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(aaa));
        long start = System.currentTimeMillis();
        System.out.println(words("hit", "cog", arrayList));
        System.out.println(new ddd().findLadders("hit", "cog", arrayList));
        long end = System.currentTimeMillis();
        System.out.println("date:" + (end - start));

    }

    /**
     * begin----->end
     * hit --->hot--->dot --->dog --->cog
     * hit --->hot--->lot --->log --->cog
     * 经过 wordList
     * 实际上是一个图
     * hit-----hot-----dot----dog----cog
     * |     |        |      |
     * |    |         |     |
     * |   |          |    |
     * lot------------log
     *
     * @param wordList
     * @param beginWord
     * @param endWord
     * @return
     */
    static int words(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        Set set = new HashSet();
        set.add(beginWord);
        queue.offer(beginWord);
        int depth = 1;
        while (!queue.isEmpty()) {
            depth++;
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                String curr = queue.poll();
                for (int k = 0; k < wordList.size(); k++) {
                    String tar = wordList.get(k);
                    //已经有的就不要再回查

                    if (set.contains(tar)) {
                        continue;
                    }
                    if (passThrough(tar, curr)) {
                        set.add(tar);
                        if (endWord.equals(tar)) {
                            return depth;
                        }
                        queue.offer(tar);
                    }
                }
            }
        }
        return 0;

    }

    static Boolean passThrough(String target, String curr) {
        int l = 0;
        for (int i = 0; i < target.length(); i++) {
            if (target.charAt(i) != curr.charAt(i)) {
                l++;
            }
        }
        return l == 1;
    }

    /**
     * begin----->end
     * 经过 wordList  记录中间的路径
     * 实际上是一个图
     * hit-----hot-----dot----dog----cog
     * |     |        |      |
     * |    |         |     |
     * |   |          |    |
     * lot------------log
     * <p>
     * 需要返回
     * hit --->hot--->dot --->dog --->cog
     * hit --->hot--->lot --->log --->cog
     *
     * @param wordList
     * @param beginWord
     * @param endWord
     * @return
     */
    static List<List<String>> findLadders1(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        List list = new ArrayList();
        Set<String> set = new HashSet<>();
        set.add(beginWord);
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                String curr = queue.poll();
                for (int k = 0; k < wordList.size(); k++) {
                    String tar = wordList.get(k);
                    //已经有的就不要再回查
                    if (set.contains(tar)) {
                        continue;
                    }
                    if (passThrough(tar, curr)) {
                        set.add(tar);
                        list.add(new ArrayList<>(set));
                        queue.offer(tar);
                    }
                }
            }
        }
        return list;
    }

    private static final int INF = 1 << 20;
    private Map<String, Integer> wordId = new HashMap<>();
    ; // 单词到id的映射
    private ArrayList<String> idWord = new ArrayList<>(); // id到单词的映射
    private ArrayList<Integer>[] edges; // 图的边


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        int id = 0;
        // 将wordList所有单词加入wordId中 相同的只保留一个 // 并为每一个单词分配一个id
        for (String word : wordList) {
            if (!wordId.containsKey(word)) {
                wordId.put(word, id++);
                idWord.add(word);
            }
        }
        // 若endWord不在wordList中 则无解
        if (!wordId.containsKey(endWord)) {
            return new ArrayList<>();
        }
        // 把beginWord也加入wordId中
        if (!wordId.containsKey(beginWord)) {
            wordId.put(beginWord, id++);
            idWord.add(beginWord);
        }

        // 初始化存边用的数组
        edges = new ArrayList[idWord.size()];
        for (int i = 0; i < idWord.size(); i++) {
            edges[i] = new ArrayList<>();
        }
        // 添加边
        for (int i = 0; i < idWord.size(); i++) {
            for (int j = i + 1; j < idWord.size(); j++) {
                // 若两者可以通过转换得到 则在它们间建一条无向边
                if (passThrough(idWord.get(i), idWord.get(j))) {
                    edges[i].add(j);
                    edges[j].add(i);
                }
            }
        }

        int dest = wordId.get(endWord); // 目的ID
        List<List<String>> res = new ArrayList<>(); // 存答案
        int[] cost = new int[id]; // 到每个点的代价
        for (int i = 0; i < id; i++) {
            cost[i] = INF; // 每个点的代价初始化为无穷大
        }

        // 将起点加入队列 并将其cost设为0
        Queue<ArrayList<Integer>> q = new LinkedList<>();
        ArrayList<Integer> tmpBegin = new ArrayList<>();
        tmpBegin.add(wordId.get(beginWord));
        q.add(tmpBegin);
        cost[wordId.get(beginWord)] = 0;

        // 开始广度优先搜索
        while (!q.isEmpty()) {
            ArrayList<Integer> now = q.poll();
            int last = now.get(now.size() - 1); // 最近访问的点
            if (last == dest) { // 若该点为终点则将其存入答案res中
                ArrayList<String> tmp = new ArrayList<>();
                for (int index : now) {
                    tmp.add(idWord.get(index)); // 转换为对应的word
                }
                res.add(tmp);
            } else { // 该点不为终点 继续搜索
                for (int i = 0; i < edges[last].size(); i++) {
                    int to = edges[last].get(i);
                    // 此处<=目的在于把代价相同的不同路径全部保留下来
                    if (cost[last] + 1 <= cost[to]) {
                        cost[to] = cost[last] + 1;
                        // 把to加入路径中
                        ArrayList<Integer> tmp = new ArrayList<>(now);
                        tmp.add(to);
                        q.add(tmp); // 把这个路径加入队列
                    }
                }
            }
        }
        return res;
    }


    /**
     * *          1
     * *    2        3
     * *  4  5      4  n
     * * n 4 n 6    nn
     * nn   nn
     * <p>
     * 1,2,4,#,4,#,#,5,#,6,#,#,3,4,#,#,#
     * #,4,#,4,#,2,#,5,#,6,#,1,#,4,#,3,#
     * #,#,#,4,4,#,#,#,6,5,2,#,#,4,#,3,1
     */
    static TreeNode returnTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);

        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.right = new TreeNode(6);
        root.left.left.right = new TreeNode(4);
        return root;
    }
    /**
     * 搜索树
     * *        4
     * *    2        6
     * *  1  3      n  7
     * *  nn  nn        nn
     *
     *
     * 4,2,1,#,#,3,#,#,6,#,7,#,#
     * #,1,#,2,#,3,#,4,#,6,#,7,#  有顺序
     * #,#,1,#,#,3,2,#,#,#,7,6,4
     */
    static TreeNode returnSearchTree() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(6);
        root.right.right= new TreeNode(7);
        return root;
    }
    static void demo() {
        System.out.println(BFS(returnTree()));
    }

    /**
     * 最短路径
     * 116   https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/
     *
     * @param root
     * @return
     */
    static int BFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                TreeNode treeNode = queue.poll();
                if (treeNode.left == null && treeNode.right == null) {
                    return depth;
                }
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
            }
            depth++;
        }
        return depth;

    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    List<TreeNode> children = new ArrayList<>();


    TreeNode() {

    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.val + "," + this.left + "," + this.right;
    }
}