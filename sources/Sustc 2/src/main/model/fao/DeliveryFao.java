package main.model.fao;

public interface DeliveryFao extends Fao {
    void _1_insertPartically(boolean verbose);

    void _2_deleteApple(boolean verbose);

    void _3_update(boolean verbose);

    void _4_selectUnfinished(boolean verbose);

    void _5_selectOnContainer(boolean verbose, String container_code);
}
