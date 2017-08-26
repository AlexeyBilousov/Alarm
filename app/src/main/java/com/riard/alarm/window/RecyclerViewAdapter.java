package com.riard.alarm.window;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.riard.alarm.R;
import com.riard.alarm.entity.Alarm;

import java.util.List;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {

    public interface SentMessageToFragment {
        void sendAlarm(Alarm alarm);
        void updateDB(Alarm alarm);
    }

    private Context context;
    private final String LOG = RecyclerViewAdapter.class.getName();
    private List<Alarm> alarms;
    private  SentMessageToFragment sentMessageToFragment;

    public RecyclerViewAdapter(List<Alarm> alarms, Fragment fragment) {
        Log.d(LOG, "Start RecyclerViewAdapter" + alarms.size());
        this.alarms = alarms;
        sentMessageToFragment = (SentMessageToFragment) fragment;
        context = fragment.getContext();
        Log.d(LOG, "Finish RecyclerViewAdapter");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG, "Start onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d(LOG, "Finish onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(LOG, "Start onBindViewHolder");
        holder.checkBoxWork.setChecked(alarms.get(position).isWork());
        holder.imageViewTypeRing.setImageResource(alarms.get(position).getTypeWakeUp());
        holder.textViewDayOfWeek.setText(alarms.get(position).getDaysOfWeek());
        holder.textViewTime.setText(alarms.get(position).getTime());
        holder.position = position;
        Log.d(LOG, "Finish onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBoxWork;
        private ImageView imageViewTypeRing;
        private TextView textViewDayOfWeek;
        private TextView textViewTime;

        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxWork = itemView.findViewById(R.id.check_box_work_alarm);
            imageViewTypeRing = itemView.findViewById(R.id.image_view_type_ring);
            textViewDayOfWeek = itemView.findViewById(R.id.text_view_days_of_week);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewTime.setOnClickListener(setAlarm);
            textViewDayOfWeek.setOnClickListener(setAlarm);
            checkBoxWork.setOnClickListener(changeWorkAlarm);
        }

        View.OnClickListener setAlarm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sentMessageToFragment.sendAlarm(alarms.get(position));
            }
        };

        View.OnClickListener changeWorkAlarm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = checkBoxWork.isChecked();
                StringBuilder stringBuilder = new StringBuilder();
                if (b) {
                    stringBuilder.append(context.getResources().getString(R.string.alarm_switch_on));
                } else {
                    stringBuilder.append(context.getResources().getString(R.string.alarm_switch_off));
                }
                alarms.get(position).setWork(b);
                sentMessageToFragment.updateDB( alarms.get(position));
                Toast.makeText(context, stringBuilder, Toast.LENGTH_SHORT).show();
            }
        };



    }
}
