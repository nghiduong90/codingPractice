package Tree;
import java.util.*;
import java.util.Map.Entry;
public class Solution {

    /**
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public static int maxProfit(int[] prices) {
        // write your code here
    	if (prices.length <= 1) return 0;
    	
    	return 0;
    }

    /**
     *@param inorder : A list of integers that inorder traversal of a tree
     *@param postorder : A list of integers that postorder traversal of a tree
     *@return : Root of a tree
     */
    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        // write your code here
    	if (inorder.length != postorder.length) return null;
       	int numOfNodes = inorder.length;
    	if (numOfNodes == 0) return null;
    	if (numOfNodes == 1) return new TreeNode(inorder[0]);

    	System.out.println ("start from root");
    	TreeNode root = buildTree (inorder, 0, numOfNodes - 1,postorder, 0, numOfNodes - 1);
    	return root;
    }
    
    private static TreeNode buildTree (int [] inorder, int inorderStartPos, int inorderEndPos, int [] postorder, int postorderStartPos, int postorderEndPos) {
     	if (inorder.length != postorder.length) return null;
    	if (inorderEndPos < inorderStartPos) return null;
       	int numOfNodes = inorderEndPos - inorderStartPos + 1;
    	if (numOfNodes == 0) return null;
    	if (numOfNodes == 1) return new TreeNode(postorder[postorderEndPos]);
 
    	int rootPos = 0;
    	TreeNode root = new TreeNode(postorder[postorderEndPos]);
    	
    	for (int i = 0; i < numOfNodes; ++i) {
    		if (inorder[i] == root.val) {
    			rootPos = i;
    			break;
    		}
    	}
    	if (rootPos == inorderStartPos) inorderStartPos++;
    	System.out.println ("rootPos = " + rootPos);
    	root.left = buildTree (inorder, inorderStartPos, rootPos - 1, postorder, postorderStartPos, rootPos - 1);
    	if (root.left != null)
    		System.out.println ("node " + root.val + " has left child is " + root.left.val );
    	root.right = buildTree (inorder, rootPos + 1 , inorderEndPos , postorder, rootPos, postorderEndPos - 1);
    	if (root.right != null)
    		System.out.println ("node " + root.val + " has right child is " + root.right.val );
    	return root;    	
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        String treeString = "";
        String nullString = "null";
        String comma = ",";
        int countNodeWithOutEmtptyNode = 1;
        
        TreeNode emptyNode = new TreeNode (-1111111111);
        int oldPos = 0;
        if (root == null) return treeString;
        LinkedList<TreeNode> listNode = new LinkedList<TreeNode>();
        listNode.add (root);
        int currentPos = 0;
        while (true) {
        	oldPos = currentPos;
        	TreeNode current = listNode.get(currentPos);
        	if (current != emptyNode) {
        		if (current.left != null) {
        			listNode.add (current.left);
        			countNodeWithOutEmtptyNode++;
        		} else {
        			listNode.add (emptyNode);
        		}
        		
        		if (current.right != null) {
        			listNode.add (current.right);
        			countNodeWithOutEmtptyNode++;
        		} else {
        			listNode.add (emptyNode);
        		}
        		currentPos++;
        	}
        	if (oldPos == currentPos) break;
        }
        
        for (int i = 0; i < listNode.size() && countNodeWithOutEmtptyNode > 0; i++) {
        	if (i == 0) {
        		treeString += Integer.toString(listNode.get(0).val);
        		countNodeWithOutEmtptyNode--;
        		continue;
        	}
        		
        	TreeNode current = listNode.get(i);
        	if (current == emptyNode) {
        		treeString += comma;
        		treeString += nullString;
        	} else {
        		treeString += comma;
        		treeString += Integer.toString(current.val);
        		countNodeWithOutEmtptyNode--;
        	}
        }
        return treeString;
    }
        	
    

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
    	if (data.length() == 0) return null;
    	TreeNode root = null;
    	int currentPos = 0;
    	String comma = ",";
    	String nullString = "null";
    	TreeNode current = null;
    	LinkedList<TreeNode> listNode = new LinkedList<TreeNode>();
    	int lastCommaPos = 0;
    	boolean foundLastComma = false;
    	for (int i = data.length() - 1; i > 0; --i) {
    		if (data.charAt(i)== ',') { 
    			lastCommaPos = i;
    			break;
    		}
    	}	
    	
    	while (currentPos < data.length()) {
    		if (foundLastComma) {
				String valString = data.substring(currentPos, data.length());
				current.left = new TreeNode (Integer.parseInt(valString));
				listNode.add (current.left);
				break;
    		}
    		int commaPos = data.indexOf(comma, currentPos);
    		if (commaPos == lastCommaPos) foundLastComma = true;
    		System.out.println ("first comma pos = " + commaPos);
    		String valString = data.substring(currentPos, commaPos);
    		currentPos = commaPos + 1;
    		System.out.println ("currentPos after first commaPos is " + currentPos);
    		
    		if (listNode.isEmpty()) {
    			root = new TreeNode(Integer.parseInt(valString));
    			listNode.add(root);
    			continue;
    		} else {
    			current = listNode.pop();
    			if (valString.equals(nullString)) {
    				current.left = null;
    			} else {
    				current.left = new TreeNode (Integer.parseInt(valString));
    				listNode.add (current.left);
    			}
    			if (!foundLastComma) {
	    			commaPos = data.indexOf(comma, currentPos);
	    			System.out.println ("currentPos = " + currentPos);
	        		valString = data.substring(currentPos, commaPos);
	        		currentPos = commaPos + 1;
	        		
	        		if (valString.equals(nullString)) {
	    				current.right = null;
	    			} else {
	    				current.right = new TreeNode (Integer.parseInt(valString));
	    				listNode.add (current.right);
	    			}
    			} else {
    				valString = data.substring(currentPos, data.length());
    				current.right = new TreeNode (Integer.parseInt(valString));
    				listNode.add (current.right);
    				break;
    			}
    		}
    	}
    	
    		
    	return root;
    }
    
    /**
     * reverse linked list recursively
     * @param head the head of linkedlist
     * @return new head of reversed linked list
     * */
    public static ListNode reverseList (ListNode head) {
    	if (head == null) return null;
    	
    	if (head.next == null) return head;
    	
    	ListNode nextNode = head.next;
    	
    	head.next = null;
    	
    	ListNode reverseHead = reverseList (nextNode);
    	
    	nextNode.next = head;
    	
    	return reverseHead;
    }
    
    
    /**
     * @param numbers : Give an array numbers of n integer
     * @return : Find all unique triplets in the array which gives the sum of zero.
     */
	/*
	 * Algorithm to follow: 
	 * 1. sort the input list
	 * 2. fix the first element as numbers[i] where i is from 0 to the list size - 2
	 * After fixing the first number, find other 2 elements by:
	 *     initialize left number to as i + 1 index: left = i + 1
	 *     initialize right number to as right most index (list size - 1): right = listSize - 1 
	 *     while (right - left > 0) {
	 *     		if (numbers[left] + numbers[right] == numbers[i]) 
	 *     			then put numbers[i], numbers[left], numbers[right] to the return list
	 *     		else if (numbers[left] + numbers[right] < numbers[i]) then left++
	 *     		else right--
	 *     }
	 * */
    public static ArrayList<ArrayList<Integer>> threeSum(int[] numbers) {
        ArrayList<ArrayList<Integer>> triplist = new ArrayList<ArrayList<Integer>>();
        if (numbers.length < 3) return triplist;
        Arrays.sort(numbers);
        for (int i = 0; i < numbers.length - 2; ++i) {
        	int first = numbers[i];
        	int left = i + 1;
        	int right = numbers.length - 1;
        	
        	while (right - left > 0) {
        		if (numbers[left] + numbers[right] == (-first)) {
        			ArrayList<Integer> ret = new ArrayList<Integer>();
        			ret.add(first);
        			ret.add(numbers[left]);
        			ret.add(numbers[right]);
        			triplist.add(ret);
        			break;
        		} else if (numbers[left] + numbers[right] < (-first)) left++;
        		else right--;
        	}
        }
        
        return triplist;
    }
    
    /**
     * @param numbers: Give an array numbers of n integer
     * @param target : An integer
     * @return : return the sum of the three integers, the sum closest target.
     */
    public static int threeSumClosest(int[] numbers ,int target) {
        // write your code here
        if (numbers.length == 1) return numbers[0];
        if (numbers.length == 2) return numbers[0] + numbers[1];
        if (numbers.length == 3) return numbers[0] + numbers[1] +numbers[2];
        Arrays.sort(numbers);
        int sum = numbers[0] + numbers[1] + numbers[2];
        int diff = Math.abs(target - sum);
        System.out.println ("initial sum = " + sum);
        for (int i = 0; i < numbers.length - 2; ++i) {
            int firstNumber = numbers[i];
            System.out.println ("first number = " + firstNumber);
            int left = i + 1;
            int right = numbers.length - 1;
            while (right - left  > 0) {
                int thisSum = firstNumber + numbers[left] + numbers[right];
                System.out.println ("\t secondNumber = " + numbers[left] + "\t thirdNumber = " + numbers[right]);
                System.out.println ("\t thisSum = " + thisSum);
                if (Math.abs(target - thisSum) < diff) {
                    sum = thisSum;
                    diff = Math.abs(target - thisSum);
                    System.out.println ("NEW sum = " + sum);
                } else if (thisSum > sum) right--;
                else left++;
                
                
            }
        }
        return sum;
    }
    /**
     * @param numbers : Give an array numbersbers of n integer
     * @param target : you need to find four elements that's sum of target
     * @return : Find all unique quadruplets in the array which gives the sum of
     *           zero.
     */
    public static ArrayList<ArrayList<Integer>> fourSum(int[] numbers, int target) {
    	ArrayList<ArrayList<Integer>> returnList = new ArrayList<ArrayList<Integer>>();
    	Set<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();
    	if (numbers.length < 4) return returnList;
    	
    	Arrays.sort(numbers);
    	for (int i = 0; i < numbers.length - 3; ++i) {
    		System.out.println ("First loop");
    		for (int j = i + 1; j < numbers.length - 2; ++j) {
    			System.out.println ("\t Second loop");
        		int left = j + 1;
        		int right = numbers.length - 1;
        		
        		while (right - left > 0) {
        			System.out.println ("\t\t third loop: first index = " + i + " second index = " + j + " third index = " + left + " fourth index = " + right);
        			int sum = numbers[i] + numbers[j] + numbers[left] + numbers[right];
        			if (sum == target) {
        				ArrayList<Integer> result = new ArrayList<Integer>();
        				result.add(numbers[i]);
        				result.add(numbers[j]);
        				result.add(numbers[left]);
        				result.add(numbers[right]);
        				if (!set.contains(result)) {
        					set.add(result);
        					returnList.add(result);
        				}
        				break;
        			} else if (sum < target) left++;
    				else right--;
        		}
        	}
    	}
    	
    	
    	return returnList;
    }
    
    public static ArrayList<ArrayList<Integer>> getPairOfSum (int[] nums, int sum) {
    	ArrayList<ArrayList<Integer>> pairList = new ArrayList<ArrayList<Integer>>();
    	if (nums.length < 2) return pairList;
    	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    	for (int i = 0; i < nums.length; ++i) {
    		if (map.containsKey(nums[i])) {
    			int freq = map.get(nums[i]);
    			map.put(nums[i], freq + 1);
    		} else {
    			map.put(nums[i], 1);
    		}
    	}
    	
    	for (Entry<Integer, Integer> entry : map.entrySet()) {
    		int firstNum = entry.getKey();
    		int firstFreq = entry.getValue();
    		if (map.containsKey(sum - firstNum)) {
    			int secondFreq = map.get(sum - firstNum);
    			int count = firstFreq > secondFreq ? secondFreq : firstFreq;
    			for (int i = 0; i < count; ++i) {
    				ArrayList<Integer> pair = new ArrayList<Integer>();
    				pair.add(firstNum);
    				pair.add(sum - firstNum);
    				pairList.add(pair);
    			}
    		}
    	}
    	return pairList;
    }
    
    private static final String ARROW = "->";
    
    /**
     * @param root the root of the binary tree
     * @return all root-to-leaf paths
     */
    
    public static List<String> binaryTreePaths(TreeNode root) {
        // Write your code here
        List<String> pathList = new LinkedList<String>();
        if (root == null) return pathList;
        StringBuilder sb = new StringBuilder();
        getPath (root, root, pathList, sb);
        
        return pathList;
    }
    
    private static void getPath (TreeNode root, TreeNode currentNode, List<String> pathList, StringBuilder sb) {
            if (currentNode == root) {
            	System.out.println ("[2] currentNode is root");
            	System.out.println ("\t Appending " + currentNode.val);
                sb.append(currentNode.val);
                System.out.println("\t\t Current string is " + sb.toString());
            } else {
            	System.out.println ("[2]current Node is not root");
                sb.append(ARROW);
                sb.append(currentNode.val);
                System.out.println("\t\t Current string is " + sb.toString());
            }
            if (currentNode.left == null && currentNode.right == null) {
            	System.out.println ("[1] Adding string " + sb.toString() + " to return list");
                pathList.add (sb.toString());            	       	
            }
            if ( currentNode.left != null) {
            	System.out.println ("[3] go left of " + currentNode.val);
            	StringBuilder newSB = new StringBuilder (sb);
            	getPath (root, currentNode.left, pathList, newSB);
            }
            
            if (currentNode.right != null) {
            	System.out.println ("[3] go right of " + currentNode.val);
            	StringBuilder newSB = new StringBuilder (sb);
            	getPath (root, currentNode.right, pathList, newSB);
            }
    }
    
    
    public static void partitionArray (int [] nums) {
    	int left = 0;
    	int right = nums.length - 1;
    	int countOdd = 0;
    	int countEven = 0;
    	while (right - left > 0) {
    		System.out.println ("left = " + left + "\t at left = " + nums[left] + "\t" + "right = " + right + "\t at right = " + nums[right]);
    		if (nums[left] % 2 == 0 && nums[right] % 2 == 1) {
    			int temp = nums[left];
    			nums[left] = nums[right];
    			nums[right] = temp;
    			countOdd++;
    			countEven++;
    			left++;
    			right--;
            } else if (nums[left] % 2 == 1){
            	countOdd++;
            	left++;
            }
            else if (nums[right] % 2 == 0){
            	countEven++;
            	right--;
            }
    	
    		printArray (nums);
    	}
    	System.out.println ("countOdd = " + countOdd);
    	System.out.println ("countEven = " + countEven);
    	Arrays.sort(nums, 0, countOdd);
    	Arrays.sort(nums,countOdd, nums.length);
    }
    
    public static ListNode partition(ListNode head, int x) {
    	if (head == null) return null;
    	if (head.next == null) return head;
    	ListNode current = head;
    	ListNode newHead = null;
    	ListNode currentNew = null;
    	ArrayList<ListNode> waitingList = new ArrayList<ListNode>();
    	
    	while (current != null) {
    		if (current.val >= x) {
    			waitingList.add(current);
    			
    		} else {
    			if (newHead == null){ 
    				newHead = new ListNode(current.val);
    				currentNew = newHead;
    		
    			} else {
    				currentNew.next = new ListNode(current.val);
    				currentNew = currentNew.next;
    			}
    		}
    		current = current.next;
    	}
    	
    	for (ListNode node : waitingList) {
    	    if (newHead == null) {
    	        newHead = node;
    	        currentNew = newHead;
    	    
    	    } else {
    		    currentNew.next = node;
    		    currentNew = currentNew.next;
    	    }
    	}
    	currentNew.next = null;
     	return newHead; 
    }
    
    /**
     * @param head: The first node of linked list.
     * @return: The head of linked list.
     */
    public static ListNode insertionSortList(ListNode head) {
        if  (head == null) return null;
    	ListNode newHead = new ListNode (head.val);

    	ListNode current = head.next;
    	
    	while (current != null) {
    		newHead = insertHere (newHead, current);
    		current = current.next;
    	}
    	
    	return newHead;
    }
    
    private static ListNode insertHere (ListNode head, ListNode node) {
    	if (head == null) return null;
    	if (node.val < head.val) {
    		ListNode temp = head;
    		head = new ListNode(node.val);
    		head.next = temp;
    		return head;
    	}
    	ListNode current = head;
    	ListNode preCurrent = head;
    	while (current != null) {
    		if (current.val <= node.val) {
    			if (current.next == null) {
    				current.next = new ListNode (node.val);
    				return head;
    			} else {
    				preCurrent = current;
    				current = current.next;
    			}
    		} else if (current.val > node.val) {
    			preCurrent.next = new ListNode (node.val);
    			preCurrent.next.next = current;
    			return head;
    		}
    	}
    	return head;
    	
    }
    
    public static TreeNode insertNode(TreeNode root, TreeNode node) {
        // write your code here
        if (root == null) {
            root = node;
            return root;
        }
        TreeNode current = root;
        while (true) {
            if (current.val == node.val) {
                return root;
            }
            if (current.val > node.val) {
                if (current.left == null) {
                    current.left = node;
                    return root;
                } else {
                    System.out.println ("go to left of " + current.val);
                    current = current.left;
                }
            } else if (current.val < node.val) {
                if (current.right == null) {
                    current.right = node;
                    return root;
                } else {
                    System.out.println ("go to right of " + current.val);
                    current = current.right;
                }
            }
        }
    }
    
    /**
     * @param root: The root of binary tree.
     * @return: True if this Binary tree is Balanced, or false.
     */
    public static boolean isBalanced(TreeNode root) {
        // write your code here
        if (root == null) return true;
        int leftHeight = getHeight (root.left) + 1;
        int rightHeight = getHeight (root.right) + 1;
        System.out.println ("leftHeight = " + leftHeight + " \t rightHeight = " + rightHeight);
        
        return Math.abs (leftHeight - rightHeight) < 2 ? true : false;
    }
    
    /**
     * function to get list of TreeNode in pre Order
     * visit --> go left --> go right
     * PreOrderTraversal is used to cloned the tree
     * @param root
     * @return 
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) return null;
        
        List<Integer> preOrderList = new LinkedList<Integer>();
        LinkedList<TreeNode> listNode = new LinkedList<TreeNode>();
        
        listNode.addFirst(root);;
        
        while (!listNode.isEmpty()) {
        	TreeNode current = listNode.pop();
        	preOrderList.add(current.val);
        	if (current.right != null) {
        		listNode.addFirst(current.right);
        	}
        	
        	if (current.left != null) {
        		listNode.addFirst(current.left);
        	}
        } 
        return preOrderList;
    }
    
    /**
     * go left --> go right --> visit
     * use to delete the tree
     * @param root
     * @return
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> postOrderList = new LinkedList<Integer>();
        if (root == null) return postOrderList;
        LinkedList<TreeNode> listNode = new LinkedList<TreeNode>();
        
        listNode.add (root);
        
        while (!listNode.isEmpty()) {
        	TreeNode current = listNode.getFirst();

        	if (current.right != null) {

        		listNode.addFirst(current.right);
        	}
        	if (current.left != null) {

        		listNode.addFirst(current.left);
        	}
        	
        	if (current.right == null && current.left == null) {
        		while (!listNode.isEmpty()) {
	        		current = listNode.pop();
	        		postOrderList.add(current.val);
	        		if (!listNode.isEmpty() && listNode.getFirst().left != current && listNode.getFirst().right != current) {
	        			break;
	        		}
        		}
        	}
        }
        return postOrderList;
    }
    
    /**
     * go left --> visit --> go right
     * print out the tree
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> inOrderList = new LinkedList<Integer>();
        if (root == null) return inOrderList;
        LinkedList<TreeNode> listNode = new LinkedList<TreeNode>();
        listNode.addFirst(root);
        
        while (!listNode.isEmpty()) {
        	TreeNode current = listNode.get(0);
        	if (current.left != null) listNode.addFirst(current.left);
        	else break;
        }
       
        
        while (!listNode.isEmpty()) {
        	TreeNode current = listNode.pop();
        	System.out.println ("current node is " + current.val);
        	inOrderList.add(current.val);
        	if (current.right != null) {
        		listNode.addFirst(current.right);
        		current = current.right;
        		while (current.left != null) {
        			current = current.left;
        			listNode.addFirst(current);
        		}
        	}
        	
        }
        
        return inOrderList;
    }
    
    
    /**
     * find the common ancestor of 2 nodes in binary tree
     * Algorithm: find the path from roots to node p and path from root 
     *            to node q. Iterate both lists at the same time, return the 
     *            one which has next one different 
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        TreeNode commonAncestor = null;
        List<TreeNode> pathP = getPathToNode (root, p.val);
        List<TreeNode> pathQ = getPathToNode (root, q.val);
        
        if (pathP == null || pathQ == null) return null;
        if (p == root || q == root) return root;
        for (int i = 0; i < pathP.size() && i < pathQ.size(); i++ ) {
        	if (pathP.get(i).val != pathQ.get(i).val) {
        		return pathP.get(i - 1);
        	} else {
        		commonAncestor = pathP.get(i);
        	}
        }
        return commonAncestor;
    }    
    
    /**
     * get the list of nodes from root to the give nodes
     * @param root
     * @param val
     * @return
     */
    public static List<TreeNode> getPathToNode (TreeNode root, int val) {
    	List<TreeNode> path = new LinkedList<TreeNode>();
    	if (root == null) return path;
    	
    	path = getPathToNode (root, val, path);
    	
    	return path;
    }
    
    private static List<TreeNode> getPathToNode (TreeNode root, int val, List<TreeNode> path) {
    	if (root == null) return path;
    	
    	path.add(root);
    	if (root.val == val) {
    		return path;
    	}
    	
    	if ((root.left != null && getPathToNode(root.left, val, path) != null) ||(root.right != null && getPathToNode(root.right, val, path) != null)) {
    		return path;
    	}
    
    	path.remove( path.size() -1);
    	
    	return null;
    }

    private static int getHeight (TreeNode thisNode) {
        if (thisNode == null) return 0;
        int leftHeight = getHeight(thisNode.left) + 1;
        int rightHeight = getHeight (thisNode.right) + 1;
        return leftHeight > rightHeight ? leftHeight : rightHeight;
    }
    
    /**
     * @param root: The root of binary tree.
     * @return: Level order a list of lists of integer
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        // write your code here
        ArrayList<ArrayList<Integer>> returnList = new ArrayList<ArrayList<Integer>>();
        if (root == null) return returnList;
        
        LinkedList<TreeNode> stack = new LinkedList<TreeNode> ();
        stack.addFirst(root);
        int numOfNodeInCurrentLevel = 1;
        
        while (!stack.isEmpty()) {
            ArrayList<Integer> listOfNode = new ArrayList<Integer>();
            int countNodeOfNewLevel = 0;
            for (int i = 0; i < numOfNodeInCurrentLevel; ++i) {
                TreeNode nodeFromStack = stack.pop();
                listOfNode.add (nodeFromStack.val);
                if (nodeFromStack.left != null) {
                    stack.add(nodeFromStack.left);
                    countNodeOfNewLevel++;
                }
                if (nodeFromStack.right != null) {
                    stack.add(nodeFromStack.right);
                    countNodeOfNewLevel++;
                }
                
            }
            numOfNodeInCurrentLevel = countNodeOfNewLevel;
            returnList.add (listOfNode);
        }
        return returnList;
    }
    
    private static void printArray (int [] nums) {
    	for (int i = 0; i < nums.length; ++i) {
    		System.out.print (nums[i] + " ");
    	}
    	System.out.print ("\n\n");
    }
    
    private static ListNode makeList(int [] nums) {
    	ListNode head = new ListNode (nums[0]);
    	ListNode current = head;
    	for (int i = 1; i < nums.length; ++i) {
    		current.next = new ListNode(nums[i]);
    		current = current.next;
    	}
    	return head;
    }
    
    private static void printList(ListNode head) {
    	while (head != null) {
    		System.out.print (head.val + " ");
    		head = head.next;
    	}
    	System.out.print("\n\n");
    }
    

    public static void main(String[] args) {
    	ListNode head = new ListNode (1);
    	head.next = new ListNode(2);
    	head.next.next = new ListNode(3);
    	head.next.next.next = new ListNode(4);
    	
    	System.out.print ("List before reversing:\t ");
    	printList(head);
    	
    	System.out.print ("List after reversing:\t ");
    	printList(reverseList(head));
    	
    	/*
    	TreeNode root = new TreeNode(1);
    	root.left = new TreeNode(2);
    	root.left.left = new TreeNode(4);
    	root.left.right = new TreeNode(5);
    	root.left.right.left = new TreeNode(6);
    	root.left.right.right = new TreeNode(7);
    	root.right = new TreeNode(3);
    	
    	//System.out.println (postorderTraversal(root));
    	List<TreeNode> path = getPathToNode (root, 7);
    	System.out.println ("path length is " + path.size());
    	for (TreeNode i : path) {
    		System.out.print (i.val + " ");
    	}
    	*/
    	//System.out.println (serialize(root));
    	
    	//String test1 = "1,2,3,4,5,null,null,null,null,6,7";
    	//root = deserialize(test1);
    	//System.out.println (root);
    	//System.out.println (serialize(root));
    	
    	
    /*	
    	int [] inorder = {1,2,3};
    	int [] postorder = {3,2,1};
    	
    	TreeNode root = buildTree (inorder, postorder);
    	
    	System.out.println ("root is " + root.val);
    */
    /*
    	TreeNode root = new TreeNode(1);
    	root.left = new TreeNode(2);
    	root.right = new TreeNode(3);
    	root.left.left = new TreeNode(4);
    	root.left.right = new TreeNode(5);
    	

    	System.out.println (isBalanced(root));
    */
    	/*=============================================== */
    	
    	/*
    	int [] nums1 = {1,2,3,4,5,6,7,8,9};
    	int [] nums2 = {0,9,8,7,6,5,4,3,2,1,10};
    	
    	System.out.print ("Before partition: \t\t");
    	printArray(nums1);
    	partitionArray(nums1);
    	System.out.print ("After partition: \t\t");
    	printArray(nums1);
    	
    	
    	System.out.print ("Before partition: \t\t");
    	printArray(nums2);
    	partitionArray(nums2);
    	System.out.print ("After partition: \t\t");
    	printArray(nums2);
    	*/
    	
    	/*=============================================== */
    	/*
    	
    	int [] test1 = {1,1};
    	ListNode head = makeList(test1);
    	System.out.print ("before sort: \t");
    	printList(head);
    	ListNode newHead =  insertionSortList (head);
    	System.out.print ("after sort: \t");
    	printList(newHead);
    	*/
    	/*
    	String s = "Nghi Duong is awesome";
    	String [] stringSplit = s.split(" ");
    	int leftPos = 0;
    	int rightPos = stringSplit.length - 1;
    	
    	while (rightPos - leftPos > 0) {
    		String str = stringSplit[leftPos];
    		stringSplit[leftPos] = stringSplit[rightPos];
    		stringSplit[rightPos] = str;
    		leftPos++;
    		rightPos--;
    	}
    	
    	String ret = "";
    	for (int i = 0; i < stringSplit.length; ++i) {
    		ret += stringSplit[i];
    		if (i != stringSplit.length - 1) ret += " ";
    	}
    	
    	System.out.println (ret);
    	
    	*/
    	
    	/*int [] test1 = {1,2,1,3,4,5,0,6,7,2};
    	System.out.println (fourSum(test1, 7));
    	*/
    	
    }
    
    
}