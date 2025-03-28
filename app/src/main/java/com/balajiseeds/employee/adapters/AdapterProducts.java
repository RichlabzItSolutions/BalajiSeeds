package com.balajiseeds.employee.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.admin.ProductActivity;
import com.balajiseeds.admin.adapters.AdapterItemCart;
import com.balajiseeds.admin.models.AddProductVariety.ProductsVarietiesWithProductsJson;
import com.balajiseeds.employee.AddLeaveActivity;
import com.balajiseeds.employee.MyCartActivity;
import com.balajiseeds.employee.fragments.FragmentProductList;
import com.balajiseeds.employee.models.ModelCart;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.PackagingOptions;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.ProductVarietiesData;
import com.balajiseeds.employee.models.addCartnew.AddToCartNewJson;
import com.balajiseeds.employee.models.getCartnew.GetCartDetails;
import com.balajiseeds.employee.models.placeOrder.PlaceOrderJson;
import com.balajiseeds.employee.models.removeQunatity.RemoveQunatityJson;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.CustomSpinnerAdapter;
import com.balajiseeds.utils.ModelSpinner;
import com.balajiseeds.utils.WebServices;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

    private OnTextChangeListener listener;
    onQtyChange onQtyChange;
    Context context;
    boolean isWhiteBG;
    List<ProductVarietiesData> productList;

    List<PackagingOptions> packagingOptionsList;
    WebServices webServices;

    String  SelectedPackSizeId = "";

    public AdapterProducts(Context context, boolean isWhiteBG,List<ProductVarietiesData> productList) {
        this.context = context;
        this.isWhiteBG = isWhiteBG;
        this.productList=productList;
        setOnchanged();
        notifyDataSetChanged();

    }


    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.listener = listener;
    }
    private void setOnchanged() {
        webServices=new WebServices(context);
        onQtyChange = new onQtyChange() {
            @Override
            public void QtyChanged(ViewHolder holder) {
                if (holder.qty == 0) {
                /*    webServices.removeFromCart(new ModelCart.deleteFromCart(productList.get(holder.getAdapterPosition()).getId(), ), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            holder.ll_count.setVisibility(View.GONE);
                            holder.tv_add.setVisibility(View.VISIBLE);
                        }
                    });*/

                } else {


                }
                holder.tv_qty.setText("" + holder.qty);

            }
        };

    }


    @NonNull
    @Override
    public AdapterProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products_with_varieties, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProducts.ViewHolder holder, int position) {
       /* if (isWhiteBG) {
            holder.rl_main.setBackgroundTintList(context.getResources().getColorStateList(android.R.color.white, context.getTheme()));
        }*/

        ProductVarietiesData product = productList.get(position);

       /* if (product.getImages().size() > 0) {
            Glide.with(context).load(product.getImages().get(0).getImageUrl()).error(R.drawable.logo).placeholder(R.drawable.loading).into(holder.iv_img);
        } else {
            Glide.with(context).load(R.drawable.logo).placeholder(R.drawable.loading).into(holder.iv_img);
        }*/


        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MyCartActivity.class);
                intent.putExtra("partyId", product.getPartyId());
                context.startActivity(intent);
               // context.startActivity(new Intent(context, MyCartActivity.class));
            }
        });


       
        //List<PackagingOptions> packagingOptions ;

        List<PackagingOptions> packagingOptions = null;

        for (int i = 0; i < productList.size(); i++) {
            //if (spinnerList.get(i).getId().equals(preLeaveId)) {
           packagingOptions  =  productList.get(i).getPackagingOptions();
            //  break;
            // }


            List<ModelSpinner> spinnerList = new ArrayList<>();
            spinnerList.add(new ModelSpinner("Select Size", "0"));
            for (PackagingOptions s : packagingOptions) {
                spinnerList.add(new ModelSpinner(s.getPackagingSize(), s.getStateId()));
            }
            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, spinnerList);
            holder.sp_packing_size.setAdapter(adapter);
            for (int j= 0; j < spinnerList.size(); j++) {
                //if (spinnerList.get(i).getId().equals(preLeaveId)) {
                holder.sp_packing_size.setSelection(j);
                //  break;
                // }
            }

            holder.sp_packing_size.setSelection(0);
            holder.sp_packing_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                      SelectedPackSizeId = selectedModel.getText();

                    Log.d("drtyu",SelectedPackSizeId);
                    // preLeaveId=SelectedLeaveId;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


        holder.tv_product_price.setText(product.getPackagingOptions().get(0).getMrp());
        holder.tv_name.setText(product.getProductCrop());
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SelectedPackSizeId.isEmpty()){
                    Toast.makeText(context, "Please Select Packing Size", Toast.LENGTH_SHORT).show();

                }
                else {
                    holder.qty++;
                    onQtyChange.QtyChanged(holder);
                    Log.d("qunadd", String.valueOf(holder.qty));

                    webServices.addCartNew(new AddToCartNewJson(productList.get(holder.getAdapterPosition()).getId(), "" + holder.qty, product.getPartyId(), SelectedPackSizeId), new WebServices.onResponses() {
                                @Override
                                public void responses(int totalItems, String cartId) {
                                    // Update the UI or handle the data
                                    Log.d("Cart Update", "Total Items: " + totalItems + ", Cart ID: " + cartId);

                                    //  notifyTotalItemsChange(totalItems);

                                 /*   if (listener != null) {
                                        listener.onTextChange(String.valueOf(totalItems));
                                    }
                                    else {
                                        listener.onTextChange(String.valueOf(totalItems));

                                    }*/


                                    holder.ll_count.setVisibility(View.VISIBLE);
                                    holder.tv_add.setVisibility(View.GONE);

                                }
                            }
                    );

                }



            }
        });
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (SelectedPackSizeId.isEmpty()){
                    Toast.makeText(context, "Please Select Packing Size", Toast.LENGTH_SHORT).show();

                }else {

                    holder.qty++;
                    onQtyChange.QtyChanged(holder);

                    webServices.addCartNew(new AddToCartNewJson(productList.get(holder.getAdapterPosition()).getId(), "" + holder.qty, product.getPartyId(), SelectedPackSizeId), new WebServices.onResponses() {
                                @Override
                                public void responses(int totalItems, String cartId) {
                                    // Update the UI or handle the data
                                    Log.d("Cart Update", "Total Items: " + totalItems + ", Cart ID: " + cartId);
                                    holder.ll_count.setVisibility(View.VISIBLE);
                                    holder.tv_add.setVisibility(View.GONE);

                                }
                            }
                    );


                }


            }
        });
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.qty > 0) {
                    holder.qty--;
                }
                onQtyChange.QtyChanged(holder);

                if (holder.qty == 0) {

                    webServices.removeFromCart(new ModelCart.deleteFromCart(productList.get(holder.getAdapterPosition()).getId(), product.getPartyId()  ), new WebServices.onResponse() {
                        @Override
                        public void response() {
                       // holder.ll_count.setVisibility(View.GONE);
                       // holder.tv_add.setVisibility(View.VISIBLE);
                        }
                    });


                }


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.SelectedProducts=productList.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, ProductActivity.class).putExtra("productid",productList.get(holder.getAdapterPosition()).getId()));
            }
        });

       // holder.itemView.setOnClickListener(v -> listener.onItemClick(data));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public interface OnTextChangeListener {
        void onTextChange(String newText);
    }

    public interface onQtyChange {
        public void QtyChanged(ViewHolder holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_add, tv_qty, tv_plus, tv_minus;
        LinearLayout ll_count;
        int qty = 0;
        boolean fav = false;
        ImageView iv_fav;
        RelativeLayout rl_main;
        ImageView iv_img;
        TextView tv_name;

        Spinner sp_packing_size;

        Button btnAddToCart;
        TextView tv_product_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);


            tv_add = itemView.findViewById(R.id.tv_add);
            tv_qty = itemView.findViewById(R.id.tvQuantity);
            tv_plus = itemView.findViewById(R.id.btnPlus);
            tv_minus = itemView.findViewById(R.id.btnMinus);
            ll_count = itemView.findViewById(R.id.ll_count);
            iv_fav = itemView.findViewById(R.id.iv_fav);
            rl_main = itemView.findViewById(R.id.rl_main);
            tv_name=itemView.findViewById(R.id.tv_product_name);
            iv_img=itemView.findViewById(R.id.iv_img);
            btnAddToCart= itemView.findViewById(R.id.btnAddToCart);
            sp_packing_size = itemView.findViewById(R.id.sp_packing_size);

        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }
    public void setProductList(List<ProductVarietiesData> productList) {
        this.productList=productList;
        setOnchanged();
        notifyDataSetChanged();

    }


}
