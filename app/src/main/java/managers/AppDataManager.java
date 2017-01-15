package managers;

import listeners.IResponseReceivedNotifyInterface;

/**
 * Created by Sanka on 9/16/16.
 */
public class AppDataManager {

    public static AppDataManager dataManager;/*
    public  LoginResponce loginResponce;
    public ClaimResponseModel claimResponseModel = null;
    public ClaimsInfo currentClaimsInfo;
    public PolicyInfoListData currentPolicyInfoListData;
    public ClaimHistoryResponseModel claimHistoryResponseModel;
    public MsgResponseModel messageResponseModel;
    public ProfileResponseViewModel profileResponseViewModel;
    public EcardResponseViewModel ecardResponseViewModel;
    public AppFeatures currentAppFeatures;
    public MasterDataResponceModel masterDataResponceModel;
    public BaseArrayList<AppFeatures> appFeatures;*/

    /**
     *
     * @return
     */
    public boolean IS_OTP_VALIDATE;

    public static AppDataManager getDataManager() {

        if (dataManager == null) {
            dataManager = new AppDataManager();
        }
        return dataManager;
    }

    /**
     * set get
     */
   /* public EcardResponseViewModel getEcardResponseViewModel() {
        return ecardResponseViewModel;
    }

    public void setEcardResponseViewModel(EcardResponseViewModel ecardResponseViewModel) {
        this.ecardResponseViewModel = ecardResponseViewModel;
    }

    public ClaimResponseModel getClaimResponseModel() {
        return claimResponseModel;
    }

    public void setClaimResponseModel(ClaimResponseModel claimResponseModel) {
        this.claimResponseModel = claimResponseModel;
    }
    public  ProfileResponseViewModel getProfileModel() {
        return profileResponseViewModel;
    }

    public  void setProfileModel(ProfileResponseViewModel profileResponseViewModel) {

        this.profileResponseViewModel = profileResponseViewModel;
    }

    public  void setLoginResponce(LoginResponce agentModel) {
        loginResponce = agentModel;
    }

    public ClaimStatus getClaimStatus(String claimStatus){

        for (ClaimStatus claimStatus1 : this.masterDataResponceModel.claimStatus){

            if (claimStatus1.statusCode.equalsIgnoreCase(claimStatus)){
                return claimStatus1;
            }
        }
        return new ClaimStatus("","");
    }


    public Currencies getCurrenciName(String currencies1){

        for (Currencies currencies : this.masterDataResponceModel.currencies){

            if (currencies.currencyCode.equalsIgnoreCase(currencies1)){
                return currencies;
            }
        }
        return new Currencies("","");
    }

    public int getCurrenciPosition(String currencies1){

        int count = this.masterDataResponceModel.currencies.size();
        for (int i = 0;i < count;i++){

            if (this.masterDataResponceModel.currencies.get(i).currencyCode.equalsIgnoreCase(currencies1)){
                return i;
            }
        }
        return 0;
    }

    public ClaimTypes getClaimTypesName(String claimTypes1){

        for (ClaimTypes claimTypes : this.masterDataResponceModel.claimTypes){

            if (claimTypes.claimTypeCode.equalsIgnoreCase(claimTypes1)){
                return claimTypes;
            }
        }
        return new ClaimTypes("","");
    }*/

    /**
     * data requests
     */

    public void login(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String userName, String password) {
        //RequestHandler.getRequestHandler().login(iResponseReceivedNotifyInterface, userName, password);
        RequestHandler.getRequestHandler().dologin(iResponseReceivedNotifyInterface, userName, password);

    }


}
