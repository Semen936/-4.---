
/*Необходимо собрать полноценное левостороннее красно-черное дерево.
И реализовать в нем метод добавления новых элементов с балансировкой.
 */

public class RedBlackTree {

    private class Node {
        private int value;
        private Color color;
        private Node leftChild;
        private Node rightChild;
    }

    private enum Color {
        RED, BLACK
    }

    private Node root;

    public boolean add(int value) { //метод добавления (обработки) рутовой ноды
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK; //корень всегда чёрный
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    /**
     * Метод добавления новой Ноды
     * @param node проверяемая нода
     * @param value значение, которое нужно добавить
     * @return true или false
     */
    private boolean addNode(Node node, int value) {
        if (node.value == value) {  //если текущая нода уже умеет указанное значение, то false, т.к. значения должны быть уникальны
            return false;
        } else {
            if (node.value > value) { //если значение текущей ноды больше, чем добавляемое, то мы запускаем рекурсивный поиск по левому ребёнку
                if (node.leftChild != null) { //если левая нода существует
                    boolean result = addNode(node.leftChild, value);
//                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else { //если левой ноды не существует, то создаем новую
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED; //все ноды при создании получают красный цвет
                    node.leftChild.value = value;
                    return true;
                }
            } else {
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                } else {
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    /**
     * Для балансировки существует 3 операции – левый малый поворот, правый малый поворот и смена цвета.
     * @param node нода, которую нужно балансировать
     * @return отбалансированная нода
     */
    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.rightChild != null && result.rightChild.color == Color.RED &&
                (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                result.rightChild != null && result.rightChild.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        }
        while (needRebalance);
        return result;
    }

    private Node leftSwap(Node node) { //левый малый поворот
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private Node rightSwap(Node node) { //правый малый поворот
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private void colorSwap(Node node) { //смена цвета  происходит, когда у ноды два красных ребёнка
        node.rightChild.color = Color.BLACK;
        node.leftChild.color = Color.BLACK;
        node.color = Color.RED;
    }


}