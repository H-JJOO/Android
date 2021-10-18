package com.koreait.first.ch07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koreait.first.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    //제네릭, Array 리스트 설명할때 얘기함, 비교하자면 화장실을 만들때 그냥 화장실을 만드느냐 아니면 제네릭으로 여자화장실 남자화장실을 만들 것이냐 라고 할수 있다.

    private List<Person> items = new LinkedList<>();

    public void addItem(Person item) { items.add(item); }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //이 부분만 바꾸면 될거임
        View itemView = inflater.inflate(R.layout.person_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Person item = items.get(position);
//        holder.setItem(item);

        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAge;

        public MyViewHolder(View v) {
            super(v);

            tvName = v.findViewById(R.id.tvName);
            tvAge = v.findViewById(R.id.tvAge);
        }

        public void setItem(Person item) {
            tvName.setText(item.getName());
            //tvAge.setText(item.getAge());//정수값은 R에서 관리하고 있는 id 정수값만 들어갈 수 있다.
//            tvAge.setText(R.string.tv_01); //이렇게 strings.xml 에서 관리하고있는 문자열을 입력할 때 정수 값을 사용한다.

            tvAge.setText(item.getAge() + "살");
        }
    }
}
