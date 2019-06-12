public class Widget {
    private String sequence;

    public Widget(String input_seq)
    {
        this.sequence = new String();
        for(int i =0 ; i < input_seq.length(); i ++)
        {
            this.sequence += input_seq.charAt(i);
        }
    }

    public int length()
    {
        return this.sequence.length();
    }

    public Widget cut(char factory)
    {
        String temp = new String();
        if(!this.sequence.isEmpty()){
            if(this.sequence.charAt(0) == factory)
            {
                temp = this.sequence.substring(1);
            }
            else {
                temp = this.sequence;
            }
        }
        return new Widget(temp);
    }

    public String getSequence()
    {
        return this.sequence;
    }
}
