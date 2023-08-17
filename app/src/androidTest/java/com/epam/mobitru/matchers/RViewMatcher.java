package com.epam.mobitru.matchers;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;
import java.util.Optional;

public class RViewMatcher {
    private final int viewId;

    public RViewMatcher(int viewId) {
        this.viewId = viewId;
    }


    public Matcher<View> elementOnView(final int pstion, final int trgtViewId) {

        return new TypeSafeMatcher<View>() {


            private Resources resources;
            private View chldView;

            public void describeTo(Description description) {
                String idDescr = Optional.ofNullable(getResources()).
                        map(items -> this.getResources().getResourceName(viewId)).
                        orElse(Integer.toString(viewId));
                description.appendText("with id: " + idDescr);
            }

            public boolean matchesSafely(View view) {

                this.setResources(view.getResources());
                if (Objects.isNull(getChldView())) {
                    Optional<View> initialResult = Optional.
                            ofNullable((RecyclerView) view.getRootView().findViewById(viewId)).
                            filter(item -> item.getId() == viewId).
                            map(item -> item.findViewHolderForAdapterPosition(pstion)).
                            map(holder -> holder.itemView);
                    if (initialResult.isPresent()) {
                        setChldView(initialResult.get());
                    } else {
                        return false;
                    }
                }
                if (trgtViewId == -1) {
                    return view == getChldView();
                } else {
                    View targetView = getChldView().findViewById(trgtViewId);
                    return view == targetView;
                }
            }

            public void setChldView(View chldView) {
                this.chldView = chldView;
            }

            public View getChldView() {
                return chldView;
            }

            public void setResources(Resources resources) {
                this.resources = resources;
            }

            public Resources getResources() {
                return resources;
            }
        }

                ;
    }
}
