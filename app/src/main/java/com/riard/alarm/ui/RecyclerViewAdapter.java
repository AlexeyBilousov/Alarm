package com.riard.alarm.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.riard.alarm.R;
import com.riard.alarm.app.App;
import com.riard.alarm.mvp.models.Alarm;
import com.riard.alarm.mvp.presenters.ItemListAlarmPresenter;
import com.riard.alarm.mvp.views.ItemListAlarmView;
import com.riard.alarm.ui.adapter.MvpAdapter;

import java.util.List;

public class RecyclerViewAdapter extends MvpAdapter<RecyclerViewAdapter.ViewHolder> implements ItemListAlarmView {
    private Context context = App.getAppComponent().getContext();
    @InjectPresenter
    ItemListAlarmPresenter itemListAlarmPresenter;

    @Override
    public void showMessageSwitcher(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public interface SentMessageToFragment {
        void sendAlarm(Alarm alarm);
    }

    private final String LOG = RecyclerViewAdapter.class.getName();
    private List<Alarm> alarms;
    private  SentMessageToFragment sentMessageToFragment;

    public RecyclerViewAdapter(List<Alarm> alarms, Fragment fragment, MvpDelegate<?> parentDelegate) {
        super(parentDelegate, String.valueOf(0));
        Log.d(LOG, "Start RecyclerViewAdapter" + alarms.size());
        this.alarms = alarms;
        sentMessageToFragment = (SentMessageToFragment) fragment;
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
                alarms.get(position).setWork(checkBoxWork.isChecked());
                itemListAlarmPresenter.switcherWorkAlarm(alarms.get(position));
            }
        };



    }
}
