package org.copy.redis.server.Enity;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class ReceiveCommand
{
    private String[] command=null;//待处理command
    private  StringBuilder RespCommand;//总是需要添加
    public ReceiveCommand(){

        RespCommand = new StringBuilder();

    }
//

    @Override
    public String toString() {
        return "ReceiveCommand{" +
                ", command=" + Arrays.toString(command) +
                ", RespCommand=" + RespCommand +
                '}';
    }
}
